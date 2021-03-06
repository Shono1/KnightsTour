import java.util.ArrayList;

// Feast on my spaghetti
public class Tour {
    private static int[][] board;
    private static final int[] horizontal = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] vertical = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static int[] knight = {0, 0};
    private static int counter = 1;
    private static int[][] acc_board = new int[8][8];
    private static int[] rand_scores = new int[1000];

    public static void main(String[] args) {
//        for(int i = 0; i < 8; i++) {
//            for(int j = 0; j < 8; j++) {
//                board = new int[8][8];
//                acc_board = new int[8][8];
//                knight[0] = i;
//                knight[1] = j;
//                update_acc_board();
//                move_acc();
//            }
//        }

//        System.out.print(get_legal_moves());
        int num_games = 0;
        while(true) {
            board = new int[8][8];
            brute_force_move();
            System.out.println(counter);
            if(counter >= 64) break;
            counter = 1;
            num_games++;
        }
        System.out.println("FINISHED: " + num_games);

    }

    private static void brute_force_move() {
        if(!(get_legal_moves().size() > 0)) {
            System.out.println("Moves: " + counter);
            //print_board();
            return;
        }
        board[knight[0]][knight[1]] = counter;
        counter++;
        ArrayList<Integer> legal = get_legal_moves();
        int index = (int) (Math.random() * legal.size());
        move(legal.get(index));
        brute_force_move();
    }

    private static void update_acc_board() {
        int[] pos = {knight[1], knight[0]};
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                knight[0] = i;
                knight[1] = j;
                if(board[i][j] == 0) acc_board[i][j] = get_legal_moves().size();
                else acc_board[i][j] = -1;
            }
        }
        knight[1] = pos[0];
        knight[0] = pos[1];
    }

    private static void move(int index) {
        if(!is_legal_move(index)) throw new IllegalArgumentException("FF");
        knight[1] += horizontal[index];
        knight[0] += vertical[index];
    }

    private static void move_counter() {
        for(int i = 0; i < 8; i++) {
            try{
                move(i);
                i = 0;
                counter++;
                board[knight[0]][knight[1]] = counter;
            } catch (IllegalArgumentException e) {
                // Do nothing with it bc we are a good programmer
            }

        }
    }

    private static void move_acc() {
        counter++;
        board[knight[0]][knight[1]] = counter;
        if(counter == 1) print_board();
        update_acc_board();
//        print_acc_board();
        ArrayList<Integer> legal = get_legal_moves();
        if(legal.size() == 0) {
            print_board();
            System.out.println("Moves: " + counter);
            counter = 0;
            return;
        }
        int min = 0;
        ArrayList<Integer> tied = new ArrayList<>();
        for(int i = 0; i < legal.size(); i++) {
            if(acc_board[knight[0] + vertical[legal.get(i)]][knight[1] + horizontal[legal.get(i)]]
                    < acc_board[knight[0] + vertical[legal.get(min)]][knight[1] + horizontal[legal.get(min)]]) {
                min = i;
                tied.clear();
                tied.add(legal.get(min));
            }
            else if (acc_board[knight[0] + vertical[legal.get(i)]][knight[1] + horizontal[legal.get(i)]]
                    == acc_board[knight[0] + vertical[legal.get(min)]][knight[1] + horizontal[legal.get(min)]]) {
                tied.add(legal.get(i));
            }
        }
        if(tied.size() > 1) min = break_tie(tied);
        move(legal.get(min));
        move_acc();
    }

    private static int break_tie(ArrayList<Integer> tied) {
        int[] pos = {knight[0], knight[1]};
        int min_index = 0;
        int min_val = 100;
        for(int i = 0; i < tied.size(); i++) {
            move(tied.get(i));
            if(get_legal_moves().size() < min_val) {
                min_index = i;
                min_val = get_legal_moves().size();
            }
            knight[0] = pos[0];
            knight[1] = pos[1];
        }
        return min_index;
    }

    private static ArrayList<Integer> get_legal_moves() {
        ArrayList<Integer> legal = new ArrayList<Integer>();

        for(int i = 0; i < horizontal.length; i++) {
            boolean chess_legal = false;
            if(((knight[1] + horizontal[i] >= 0) && (knight[1] + horizontal[i] < 8)) && ((knight[0] + vertical[i] >= 0) && (knight[0] + vertical[i] < 8))) {
                chess_legal = true;
            }
            if(chess_legal) {
                if(board[knight[0] + vertical[i]][knight[1] + horizontal[i]] == 0) legal.add(i);
            }
        }
        return legal;
    }

    private static Boolean is_legal_move(int index) {
        ArrayList<Integer> legal = get_legal_moves();
        boolean chess_legal = false;
        for(int move : legal) {
            if(horizontal[move] == horizontal[index] && vertical[move] == vertical[index]) chess_legal = true;
        }
        if(!chess_legal) return false;

        if(board[knight[0] + vertical[index]][knight[1] + horizontal[index]] != 0) return false;
        return true;
    }

    private static void print_board() {
        System.out.println("Board:\n");
        for(int[] row : board) {
            for(int square : row) System.out.printf("%4d", square);
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    private static void print_acc_board() {
        System.out.println("Acc Board:\n");
        for(int[] row : acc_board) {
            for(int square : row) System.out.printf("%4d", square);
            System.out.print("\n");
        }
        System.out.println("\n");
    }
}
