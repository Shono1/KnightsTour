import java.util.ArrayList;

public class Tour {
    private static int[][] board = new int[8][8];
    private static final int[] horizontal = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] vertical = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static int[] knight = {0, 0};
    private static int counter = 1;

    public static void main(String[] args) {
        board[0][0] = 1;
        move_counter();
        print_board();
    }

    private static void move(int index) {
        if(!is_legal_move(index)) throw new IllegalArgumentException("ffffffffffffffffff");
        knight[0] += horizontal[index];
        knight[1] += vertical[index];
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

    private static ArrayList<Integer> get_legal_moves() {
        ArrayList<Integer> legal = new ArrayList<Integer>();
        for(int i = 0; i < horizontal.length; i++) {
            if(((knight[0] + horizontal[i] >= 0) && (knight[0] + horizontal[i] < 8)) && ((knight[1] + vertical[i] >= 0) && (knight[1] + vertical[i] < 8))) {
                legal.add(i);
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

        if(board[knight[0] + horizontal[index]][knight[1] + vertical[index]] != 0) return false;
        return true;
    }

    private static void print_board() {
        for(int[] row : board) {
            for(int square : row) System.out.printf("%4d", square);
            System.out.print("\n");
        }
    }
}
