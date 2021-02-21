import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    //Tamanho máximo da árvore Fenwick
    final static int MAXN = 100010;
    //Vetores para processamento final da árvore de Fenwick
    static Vector<Trinca> sweep = new Vector<>(), atu = new Vector<>(),
            joinedFunds = new Vector<>();
    //Vetor de riquezas
    static Vector<Long> comp = new Vector<>();
    //Árvore de Fenwick
    static long[] BITree = new long[MAXN];
    //Vetores para os índices de "e1" = Beleza, "e2" = Riqueza e "e3" = Doação
    static long[] e1 = new long[MAXN], e2 = new long[MAXN], e3 = new long[MAXN];
    //Fundo máximo arrecadado
    static long fundoMax = 0;

    public static void main(String[] args) {
        //Atributos
        Scanner sc = new Scanner(System.in);
        //Quantidade de pessoas a serem convidadas
        int inviteListQuantity = sc.nextInt();
        //Avança para a próxima linha depois do inteiro que representa a quantidade de possíveis convidados
        sc.nextLine();

        //Pega as informações das pessoas
        for (int i = 0; i < inviteListQuantity; i++) {
            String[] val = sc.nextLine().split(" ");
            //Passa cada uma das informações informadas para as devidas variáveis
            e1[i] = Long.parseLong(val[0]);
            e2[i] = Long.parseLong(val[1]);
            e3[i] = Long.parseLong(val[2]);
            //Adiciona o índice de riqueza à uma lista separada
            comp.add(e2[i]);
        }

        //Ordena a coleção dos valores de riquezas
        comp.sort(null);

        for (long i = 0; i < inviteListQuantity; i++) {
            //Substitui o índice de riqueza pelo índice em ordem de prioridade da lista de riquezas
            e2[(int) i] = lower_bound(comp, e2[(int) i]) + 1;
            //Adiciona à Matrix sweep, que contém os dados pré-processados
            sweep.add(new Trinca(e1[(int) i], e2[(int) i], e3[(int) i]));
        }

        //Ordena a matrix sweep.
        sweep.sort(null);

        //Mescla os fundos em uma única linha caso haja alguém com beleza e riqueza iguais seja encontrado
        for (int i = 0; i < inviteListQuantity; i++) {
            //Caso o agoraVai não esteja vazio e a pessoa possui riqueza e belezas iguais, somam-se os fundos
            if (!joinedFunds.isEmpty() &&
                    joinedFunds.lastElement().getFirstElement() == sweep.get(i).getFirstElement() &&
                    joinedFunds.lastElement().getSecondElement() == sweep.get(i).getSecondElement()) {
                joinedFunds.lastElement().thirdElement += sweep.get(i).thirdElement;
            } else {
                //Senão, adiciona normalmente do sweep para a matriz
                joinedFunds.add(sweep.get(i));
            }
        }

        /*
         * Adiciona os campos nulos que estão em falta, usa como base a matriz completa, e sobrescreve com a matriz
         * de fundos unidos caso seja diferente do index iterado
         * */
        while (joinedFunds.size() < sweep.size()) {
            joinedFunds.add(sweep.get(joinedFunds.size()));
        }

        //Realiza a última operação
        for (int i = 0; i < inviteListQuantity; i++) {
            /*
             * Define a variável x como o índice de beleza
             * Define a variável y como o índice de riqueza
             * Define a variável val como o valor a ser doado
             * */
            long x = sweep.get(i).getFirstElement(), y = sweep.get(i).getSecondElement(), val = sweep.get(i).getThirdElement();

            /*
             * Caso haja algum valor na matriz final, chamada de atu, e o índice de beleza seja menor que o do próximo
             * convidado, executa a atualização da árvore
             * */
            while (!atu.isEmpty() && (atu.lastElement().getFirstElement() < x)) {
                //Atualiza a árvore
                update((int) atu.lastElement().getSecondElement(),
                        atu.lastElement().getThirdElement());
                //Remove o elemento do atual
                atu.removeElementAt(atu.size() - 1);
            }

            /*
             * Pega o valor da árvore e insere na matriz final, com a soma do valor encontrado na árvore, e o
             * valor atual do convidado
             * */
            long valFromTree = sum((int) (y - 1)) + val;
            atu.add(new Trinca(x, y, valFromTree));

            //Adiciona ao valor máximo o maior número
            fundoMax = max(fundoMax, valFromTree);
        }

        //Exibe por fim, o valor máximo possível do fundo, seguindo os critérios do desafio
        System.out.println(fundoMax);
    }

    //Atualiza a árvore
    public static void update(int idx, long delta) {
        while (idx < MAXN) {
            BITree[idx] = max(BITree[idx], delta);
            idx += (idx & (-idx));
        }
    }

    //Soma as informações desejadas da árvore
    public static long sum(int idx) {
        long sum = 0L;
        while (idx > 0) {
            sum = max(BITree[idx], sum);
            idx -= (idx & (-idx));
        }
        return sum;
    }

    //Retorna um iterador que aponta para o primeiro elemento do array, [primeiro, último],
    // o qual tem um valor não menor que o valor fornecido
    public static long lower_bound(Vector<Long> ar, long k) {
        long s = 0;
        long e = ar.size();
        while (s != e) {
            long mid = s + e >> 1;
            if (ar.get((int) mid) < k) {
                s = mid + 1;
            } else {
                e = mid;
            }
        }
        if (s == ar.size()) {
            return -1;
        }
        return s;
    }

    //Pega o maior valor entre 2 Longs
    static long max(long a, long b) {
        return Math.max(a, b);
    }

    //Classe que implementa a Trinca, matriz 1 X 3.
    static class Trinca implements Comparable {
        public long firstElement;
        public long secondElement;
        public long thirdElement;

        //Método construtor
        Trinca(long _firstElement, long _secondElement, long _thirdElement) {
            firstElement = _firstElement;
            secondElement = _secondElement;
            thirdElement = _thirdElement;
        }

        //Getters
        long getFirstElement() {
            return firstElement;
        }

        long getSecondElement() {
            return secondElement;
        }

        long getThirdElement() {
            return thirdElement;
        }

        //Comparador oriundo da classe "Comparable"
        @Override
        public int compareTo(Object other) {
            return Comparator.comparing(Trinca::getFirstElement)
                    .thenComparing(Trinca::getSecondElement)
                    .thenComparingLong(Trinca::getThirdElement)
                    .compare(this, (Trinca) other);
        }
    }
}