import java.io.Serializable;
import java.util.*;

class Main {
    //Tamanho máximo da árvore
    final static int MAXN = (int) (Math.pow(10, 5) + 10);

    static Vector<Triplet<Long, Long, Long>> sweep = new Vector<>();
    static Long sweepM[][];
    static Vector<Triplet<Long, Long, Long>> atu = new Vector<>();
    static Vector<Long> comp = new Vector<>();
    static Long[] bit = new Long[MAXN], e1 = new Long[MAXN], e2 = new Long[MAXN], e3 = new Long[MAXN];
    static Long resp = 0L;

    public static void main(String[] args) {
        //Atributos
        Scanner sc = new Scanner(System.in);
        //Quantidade de pessoas a serem convidadas
        int invited = sc.nextInt();
        sc.nextLine();

        sweepM = new Long[invited][3];

        //Pega as informações das pessoas
        for (int i = 0; i < invited; i++) {
            String[] val = sc.nextLine().split(" ");
            //Passa cada uma das informações informadas para as devidas variáveis
            e1[i] = Long.parseLong(val[0]);
            e2[i] = Long.parseLong(val[1]);
            e3[i] = Long.parseLong(val[2]);
            //Adiciona o índice de riqueza à uma lista separada
            comp.add(e2[i]);
        }

        for (int i = 0; i < invited; i++) {
            System.out.print(comp.get(i) + "\t");
        }

        //Ordena a coleção dos valores de riquezas
        Collections.sort(comp);
        System.out.println();
        for (int i = 0; i < invited; i++) {
            System.out.print(comp.get(i) + "\t");
        }

        System.out.print("\n--- end comp ---\n\n");

        for (int i = 0; i < invited; i++) {
            //Faz uma pesquisa binária no número
            System.out.print(e2[i] + ", Índice: " + Collections.binarySearch(comp, e2[i]) + "\t");
            System.out.print("Comp begin: " + comp.firstElement() + "\t");
            System.out.print("Result: " + (Collections.binarySearch(comp, e2[i]) - comp.firstElement() + 1) + "\n");
            //e2[i] = Collections.binarySearch(comp, e2[i]) - comp.firstElement() + 1;
            System.out.println(comp.indexOf(e2[i]));
            e2[i] = (long) comp.indexOf(e2[i]) + 1;
            System.out.print(e2[i] + ", After: " + (comp.indexOf(e2[i]) + 1) + "\n\n");
            //Adiciona ao sweep
            sweep.add(new Triplet<>(e1[i], e2[i], e3[i]));
            //Adiciona à Matrix
            sweepM[i][0] = e1[i];
            sweepM[i][1] = e2[i];
            sweepM[i][2] = e3[i];
        }

        for (int i = 0; i < invited; i++) {
            System.out.print("B: " + sweep.get(i).getFirst() + "\t");
            System.out.print("R: " + sweep.get(i).getSecond() + "\t");
            System.out.print("F: " + sweep.get(i).getThird() + "\n");
        }

        Vector<Triplet<Long, Long, Long>> agoraVai = new Vector<>();
        System.out.println("---");

        for (int i = 0; i < invited; i++) {
            System.out.print("B: " + sweep.get(i).getFirst() + "\t");
            System.out.print("R: " + sweep.get(i).getSecond() + "\t");
            System.out.print("F: " + sweep.get(i).getThird() + "\n");
        }

        System.out.println("--- The power of a Matrix ---");
        for (int i = 0; i < invited; i++) {
            System.out.print("B: " + sweepM[i][0] + "\t");
            System.out.print("R: " + sweepM[i][1] + "\t");
            System.out.print("F: " + sweepM[i][2] + "\n");
        }

        sweep.sort(Comparator.comparing(t -> t.getFirst() <= t.getSecond()));
//        Arrays.sort(sweepM, Comparator.comparingLong(o -> o[0]).thenComparingLong(o -> o[1]));
//        sortbyColumn(sweepM, 1);
        sortbyColumn(sweepM, 0);

        System.out.println("--- The power of a Matrix Ordered ---");
        for (int i = 0; i < invited; i++) {
            System.out.print("B: " + sweepM[i][0] + "\t");
            System.out.print("R: " + sweepM[i][1] + "\t");
            System.out.print("F: " + sweepM[i][2] + "\n");
        }

        //Mescla os fundos em uma única linha caso haja alguém com beleza e riqueza iguais seja encontrado
        for (int i = 0; i < invited; i++) {
            //Caso o agoraVai não esteja vazio e a pessoa possui riqueza e belezas iguais, somam-se os fundos
            if (!agoraVai.isEmpty() &&
                    agoraVai.get(agoraVai.size() - 1).getFirst().equals(sweep.get(i).getFirst()) &&
                    agoraVai.get(agoraVai.size() - 1).getSecond().equals(sweep.get(i).getSecond())) {
                //Cria uma variável auxiliar que soma o fundo com o resultado das belezas e riquezas iguais
                Triplet<Long, Long, Long> aux = new Triplet<>(agoraVai.get(agoraVai.size() - 1).getFirst(),
                        agoraVai.get(agoraVai.size() - 1).getSecond(),
                        agoraVai.get(agoraVai.size() - 1).getThird() + sweep.get(i).getThird());
                //Adiciona no agora vai na posição de onde estava
                agoraVai.set(agoraVai.size() - 1, aux);
            } else {
                //Senão, adiciona normalmente no sweep
                agoraVai.add(sweep.get(i));
            }
        }

        //Arrays.sort(new Vector[]{sweep});
        sweep = agoraVai;
        for (int i = 0; i < invited; i++) {
            Long x = sweep.get(i).getFirst(),
                    y = sweep.get(i).getSecond(),
                    val = sweep.get(i).getThird();

            while (!atu.isEmpty() && (atu.get(atu.size() - 1).getFirst() < x)) {
                //Atualiza a árvore
                update(atu.get(atu.size() - 1).getSecond().intValue(),
                        atu.get(atu.size() - 1).getThird());
                //Remove o elemento do atual
                atu.remove(atu.size() - 1);
            }

            System.out.println("Soma encontrada: " + sum(y.intValue()));
            long davez = sum(y.intValue() - 1) + val;
            atu.add(new Triplet<>(x, y, davez));

            resp = Math.max(resp, davez);
        }

        System.out.println(resp);
    }

    //Atualiza a árvore
    public static void update(int idx, long delta) {
        while (idx < MAXN) {
            bit[idx] = Math.max(bit[idx] != null ? bit[idx] : 0, delta);
            idx += (idx & (-idx));
        }
    }

    //Soma as informações desejadas na árvore
    public static long sum(int idx) {
        long sum = 0;
        while (idx > 0 && bit[idx] != null) {
            sum += Math.max(bit[idx], sum);
            idx -= (idx & (-idx));
        }
        return sum;
    }

    // Function to sort by column
    public static void sortbyColumn(Long arr[][], int col)
    {
        //Ordena pela segunda e depois pela primeira coluna
        Arrays.sort(arr, (entry1, entry2) -> {
            if (entry1[col] >= entry2[col] && (entry1[col] > entry2[col] || entry1[col + 1] >= entry2[col + 1]))
                return 1;
            else
                return -1;
        });
    }
}

//A tuple of three elements.
class Triplet<F, S, T> {

    private final F first;
    private final S second;
    private final T third;

    public Triplet(final F first, final S second, final T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public T getThird() {
        return third;
    }

    @Override
    public String toString() {
        return "Triplet{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;

        if (!Objects.equals(first, triplet.first)) return false;
        if (!Objects.equals(second, triplet.second)) return false;
        return Objects.equals(third, triplet.third);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        return result;
    }
}

//public class Main {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in, Charset.defaultCharset());
//        int invited = sc.nextInt();
//        AtomicInteger maxDonate = new AtomicInteger(0);
//        Map<Integer, ArrayList<Integer>> invitedDetails = new HashMap<>();
//        int index = 0;
//
//        for (int i = 0; i <= invited; i++) {
//            ArrayList<Integer> detail = new ArrayList<>();
//            Arrays.stream(sc.nextLine().split(" ")).filter((val) -> !val.equals(""))
//                    .forEach(number -> {
//                        detail.add(Integer.parseInt(number));
//                    });
//
//            if (detail.size() != 0) {
//                invitedDetails.put(index, detail);
//                index++;
//            }
//        }
//
//        invitedDetails.forEach((person, detail) -> {
//            boolean moreRichAndLessBeauty = false, moreBeautyAndLessRich = false, moreBeautyAndAndRich = false,
//                    richAndLessBeauty = false, equals = false;
//
//            for (int i = 0; i < invitedDetails.size(); i++) {
//                if (!detail.equals(invitedDetails.get(i))) {
//                    //Verifica se há alguma pessoa mais rica e menos bonita
//                    if (detail.get(1) < invitedDetails.get(i).get(1) && detail.get(0) > invitedDetails.get(i).get(0))
//                        moreRichAndLessBeauty = true;
//                    //Verifica se há alguma pessoa mais bonita e menos rica
//                    if (detail.get(0) < invitedDetails.get(i).get(0) && detail.get(1) > invitedDetails.get(i).get(1))
//                        moreBeautyAndLessRich = true;
//                    //Verifica se há alguma pessoa mais bonita e mais rica
//                    if (detail.get(0) > invitedDetails.get(i).get(0) && detail.get(1) > invitedDetails.get(i).get(1))
//                        moreBeautyAndAndRich = true;
//                    //Verifica se há alguém com a mesma riqueza e beleza ao mesmo tempo
//                    if (detail.get(0) == detail.get(1) && invitedDetails.get(i).get(0) == invitedDetails.get(i).get(1))
//                        equals = true;
//                    //Verifica se a beleza é menor que a riqueza
//                    if (detail.get(1) > detail.get(0)) richAndLessBeauty = true;
//                }
//            }
//
//            if (moreBeautyAndAndRich || equals) maxDonate.addAndGet(detail.get(2));
//        });
//
//        System.out.println(maxDonate.get());
//    }
//}

//class Main2 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in, Charset.defaultCharset());
//        int invitedNumber = sc.nextInt(), amount = 0, fund = 0, beauty = 0, rich = 0;
//        ArrayList<String> listNumbers = new ArrayList<>();
//        sc.nextLine();
//
//        for (int i = 0; i < invitedNumber; i++) {
//            listNumbers.add(sc.nextLine());
//        }
//
//        for (int i = 0; i < invitedNumber; i++) {
//            boolean addFund = false;
//            String[] numbers = listNumbers.get(i).split(" ");
//            beauty = Integer.parseInt(numbers[0]);
//            rich = Integer.parseInt(numbers[1]);
//            fund = Integer.parseInt(numbers[2]);
//
//            for (int j = 0; j < invitedNumber; j++) {
//                String[] numbersJ = listNumbers.get(j).split(" ");
//                int beautyJ = Integer.parseInt(numbersJ[0]);
//                int richJ = Integer.parseInt(numbersJ[1]);
//                int fundJ = Integer.parseInt(numbersJ[2]);
//
//                if (!numbers.equals(numbersJ)) {
//                    if (!(beauty > beautyJ && rich > richJ || (rich == beauty) && (richJ == beautyJ))) addFund = true;
//                }
//            }
//
//            if (addFund) amount += fund;
//        }
//
//        System.out.println(amount);
//    }
//}

//class Main3 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in, Charset.defaultCharset());
//        int invitedNumber = sc.nextInt();
//        int totalFund = 0;
//        ArrayList<String> listInvited = new ArrayList<>();
//        ArrayList<String> finalInvited = new ArrayList<>();
//        sc.nextLine();
//
//        for (int i = 0; i < invitedNumber; i++) {
//            listInvited.add(sc.nextLine());
//        }
//
//        for (int i = 0; i < invitedNumber; i++) {
//            String[] indexes = listInvited.get(i).split(" ");
//            boolean anyMoreBeautyAndLessRich = false, anyLessBeautyAndMoreRich = false;
//        }
//    }
//}