import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;

import java.util.ArrayList;

public class SafwaffaGamTstsgerg {
    public static void main(String[] args) {

        SafwaffaGamTstsgerg a = new SafwaffaGamTstsgerg();

        ArrayList<Integer> adsdsa = new ArrayList<>(10);
        adsdsa.add(15);
        adsdsa.add(15);
        adsdsa.add(15);
        adsdsa.add(21);
        System.out.println(adsdsa);

    }


    private static void A(Integer[][] boardStateEx) {
        Integer[][] newBoardState = new Integer[6][7];
        System.arraycopy(boardStateEx, 0, newBoardState, 0, boardStateEx.length);



        //System.out.println(aa(2));

    }

    public int test(int a) {
        if (a == 0) {
            return 0;
        } else {
            System.out.println(a);
            return test(a - 1);
        }
    }
}




/*
int m = a.colCount/2;
        for(int i = 0; i < 1000; i++){
            for(int p = 0; p < 200; p++){
                m = m + (int)((Math.random() < 0.5 ? -0.5:0.5) + (Math.random() < 0.5 ? -0.5:0.5));
            }
            if(!(a.dropPiece(m,1))){
                System.out.println("no " + m);
            }

            m = a.colCount/2;
        }
        a.printBoardState();
 */

