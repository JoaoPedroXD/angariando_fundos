import java.util.*;

public class Main {
    //Tamanho máximo da árvore Fenwick
    final static int MAXN = (int) (Math.pow(10, 5) + 10);
    //Vetores para processamento final da árvore de Fenwick
    static Long sweep[][], atu[][];
    //Vetor de riquezas
    static Vector<Long> comp = new Vector<>();
    //Árvore de Fenwick
    static int[] BITree = new int[MAXN];
    //Vetores para os índices de "e1" = Beleza, "e2" = Riqueza e "e3" = Doação
    static Long[] e1 = new Long[MAXN], e2 = new Long[MAXN], e3 = new Long[MAXN];
    //Fundo máximo arrecadado
    static Long fundoMax = 0L;

    public static void main(String[] args) {
        //Atributos
        Scanner sc = new Scanner(System.in);
        //Quantidade de pessoas a serem convidadas
        int inviteListQuantity = sc.nextInt();
        //Avança para a próxima linha depois do inteiro que representa a quantidade de possíveis convidados
        sc.nextLine();

        //Inicializa as variáveis dos arrays bidimensionais
        sweep = new Long[inviteListQuantity][3];
        Long[][] joinedFunds = new Long[inviteListQuantity][3];
        atu = new Long[inviteListQuantity][3];

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
        Collections.sort(comp);

        for (int i = 0; i < inviteListQuantity; i++) {
            //Substitui o índice de riqueza pelo índice em ordem de prioridade da lista de riquezas
            e2[i] = (long) comp.indexOf(e2[i]) + 1;
            //Adiciona à Matrix sweep, que contém os dados pré-processados
            sweep[i][0] = e1[i];
            sweep[i][1] = e2[i];
            sweep[i][2] = e3[i];
        }

        //Ordena a matrix sweep.
        sortByColumn(sweep, 2); //Primeiro na 3ª coluna
        sortByColumn(sweep, 1); //Depois pela 2ª coluna
        sortByColumn(sweep, 0); //E por fim, na 1ª coluna

        //Variável que auxilia caso algum número seja mesclado seja encontrado, deve iniciar em 1
        int aux = 1;

        //Mescla os fundos em uma única linha caso haja alguém com beleza e riqueza iguais seja encontrado
        for (int i = 0; i < inviteListQuantity; i++) {
            //Caso o agoraVai não esteja vazio e a pessoa possui riqueza e belezas iguais, somam-se os fundos
            if (i > 0 &&
                    joinedFunds[i - aux][0].equals(sweep[i][0]) &&
                    joinedFunds[i - aux][1].equals(sweep[i][1])) {
                joinedFunds[i - aux][2] += sweep[i][2];
                aux++;
            } else {
                //Senão, adiciona normalmente do sweep para a matriz
                joinedFunds[i - (aux - 1)][0] = sweep[i][0];
                joinedFunds[i - (aux - 1)][1] = sweep[i][1];
                joinedFunds[i - (aux - 1)][2] = sweep[i][2];
            }
        }

        /*
         * Adiciona os campos nulos que estão em falta, usa como base a matriz completa, e sobrescreve com a matriz
         * de fundos unidos
         * */
        for (int i = 0; i < inviteListQuantity; i++) {
            if (joinedFunds[i][0] != null) sweep[i] = joinedFunds[i];
        }

        //Variável que auxilia o preenchimento da matriz Atu, que faz comparações para a árvore de Fenwick
        int auxAtu = 0;

        //Realiza a última operação
        for (int i = 0; i < inviteListQuantity; i++) {
            /*
             * Define a variável x como o índice de beleza
             * Define a variável y como o índice de riqueza
             * Define a variável val como o valor a ser doado
             * */
            Long x = sweep[i][0],
                    y = sweep[i][1],
                    val = sweep[i][2];

            /*
            * Caso haja algum valor na matriz final, chamada de atu, e o índice de beleza seja menor que o do próximo
            * convidado, executa a atualização da árvore
            * */
            while (auxAtu != 0 && (atu[auxAtu - 1][0] < x)) {
                //Atualiza a árvore
                update(atu[auxAtu - 1][1].intValue(),
                        atu[auxAtu - 1][2].intValue());
                //Remove o elemento do atual
                atu[auxAtu - 1][0] = null;
                atu[auxAtu - 1][1] = null;
                atu[auxAtu - 1][2] = null;
                auxAtu--;
            }

            /*
            * Pega o valor da árvore e insere na matriz final, com a soma do valor encontrado na árvore, e o
            * valor atual do convidado
            * */
            long valFromTree = sum(y.intValue() - 1) + val;
            atu[auxAtu][0] = x;
            atu[auxAtu][1] = y;
            atu[auxAtu][2] = valFromTree;
            auxAtu++;

            //Adiciona ao valor máximo o maior número
            fundoMax = Math.max(fundoMax, valFromTree);
        }

        //Exibe por fim, o valor máximo possível do fundo, seguindo os critérios do desafio
        System.out.println(fundoMax);
    }

    //Atualiza a árvore
    public static void update(int idx, int delta) {
        while (idx < MAXN) {
            BITree[idx] = Math.max(BITree[idx], delta);
            idx += (idx & (-idx));
        }
    }

    //Soma as informações desejadas da árvore
    public static long sum(int idx) {
        long sum = 0;
        while (idx > 0) {
            sum = Math.max(BITree[idx], sum);
            idx -= (idx & (-idx));
        }
        return sum;
    }

    //Função para ordenar por uma coluna do array
    public static void sortByColumn(Long arr[][], int col) {
        //Ordena pela segunda e depois pela primeira coluna
        Arrays.sort(arr, Comparator.comparing(longs -> longs[col]));
    }
}