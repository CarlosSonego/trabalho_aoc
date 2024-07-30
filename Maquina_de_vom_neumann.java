import java.util.Scanner;

public class Maquina_de_vom_neumann {
    static final int tamanho_maximo_de_instrucoes = 250;
    static final int tamanho_maximo_de_memoria = 250;
    static String[] instrucoes = new String[tamanho_maximo_de_instrucoes];
    static int[] memoria = new int[tamanho_maximo_de_memoria];
    static int numInstrucoes = 0;
    static int PC = 0;
    static int MBR = 0;
    static int MAR = 0;
    static String IR = "";
    static boolean zero = false;
    static boolean negativo = false;

    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("------------------");
            System.out.println("      OPCOES:     ");
            System.out.println("------------------");
            System.out.println("1 - INSERIR       ");
            System.out.println("2 - VER INSTRUCOES");
            System.out.println("3 - VER MEMORIA   ");
            System.out.println("4 - EXECUTAR      ");
            System.out.println("5 - LIMPAR        ");
            System.out.println("6 - SAIR          ");
            System.out.print("Escolha uma opcao: ");
            opcao = leia.nextInt();
            leia.nextLine();

            if (opcao == 1) {
                inserirInstrucao(leia);
            } else if (opcao == 2) {
                verInstrucoes();
            } else if (opcao == 3) {
                verMemoria();
            } else if (opcao == 4) {
                executarInstrucoes();
            } else if (opcao == 5) {
                limparTudo();
            } else if (opcao == 6) {
                System.out.println("Bye");
            } else {
                System.out.println("Selecione uma opcao do menu valida");
            }
        } while (opcao != 6);

        leia.close();
    }

    public static void inserirInstrucao(Scanner leia) {
        if (numInstrucoes >= tamanho_maximo_de_instrucoes) {
            System.out.println("Numero maximo de instrucoes atingido.");
            return;
        }

        String codigo;
        do {
            System.out.print("Digite o codigo da instrucao (001100 para terminar as instrucoes): ");
            codigo = leia.nextLine();
            if (!codigo.equals("001100")) {
                System.out.print("Digite o operando 1: ");
                String operando1 = leia.nextLine();
                System.out.print("Digite o operando 2: ");
                String operando2 = leia.nextLine();
                String instrucao = codigo + " " + operando1 + " " + operando2;
                instrucoes[numInstrucoes] = instrucao;
                numInstrucoes++;
            }
        } while (!codigo.equals("001100"));
    }

    public static void verInstrucoes() {
        System.out.println("|Codigo|Operando 1|Operando 2|Resultado                  |");
        System.out.println("|000001|#pos      |          |MBR <- #pos                |");
        System.out.println("|000010|#pos      |#dado     |#pos <- #dado              |");
        System.out.println("|000011|#pos      |          |MBR <- MBR + #pos          |");
        System.out.println("|000100|#pos      |          |MBR <- MBR - #pos          |");
        System.out.println("|000101|#pos      |          |MBR <- MBR * #pos          |");
        System.out.println("|000110|#pos      |          |MBR <- MBR / #pos          |");
        System.out.println("|000111|#lin      |          |JUMP to #lin               |");
        System.out.println("|001000|#lin      |          |JUMP IF Z to #lin          |");
        System.out.println("|001001|#lin      |          |JUMP IF N to #lin          |");
        System.out.println("|001010|          |          |MBR <- raiz quadratica(MBR)|");
        System.out.println("|001011|          |          |MBR <- -MBR                |");
        System.out.println("|001111|#pos      |          |#pos <- MBR                |");
        System.out.println("|001100|          |          |NOP                        |");
        System.out.println();
    }

    public static void verMemoria() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>Memoria<<<<<<<<<<<<<<<<<<<<");
        for (int i = 0; i < tamanho_maximo_de_memoria; i++) {
            System.out.printf("Endereco %d: %d\n", i, memoria[i]);
        }
    }

    public static void executarInstrucoes() {
        while (PC < numInstrucoes) {

            System.out.println("Ciclo de Instrucao " + (PC + 1) + ":");
            System.out.println("PC = " + PC);
            System.out.println("MAR = " + MAR);
            System.out.println("MBR = " + MBR);
            System.out.println("IR = " + IR);

            String[] aux = instrucoes[PC].split(" ");
            IR = aux[0];

            if (IR.equals("000001")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                MBR = memoria[MAR];
                System.out.println("exercendo oredem: MBR <- memoria[" + MAR + "] = " + MBR);
            } else if (IR.equals("000010")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                MBR = Integer.parseInt(aux[2].replace("#", ""));
                memoria[MAR] = MBR;
                System.out.println("exercendo oredem: memoria[" + MAR + "] <- " + MBR);
            } else if (IR.equals("000011")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                MBR += memoria[MAR];
                System.out.println("exercendo oredem: MBR <- MBR + memoria[" + MAR + "] = " + MBR);
            } else if (IR.equals("000100")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                MBR -= memoria[MAR];
                System.out.println("exercendo oredem: MBR <- MBR - memoria[" + MAR + "] = " + MBR);
            } else if (IR.equals("000101")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                MBR *= memoria[MAR];
                System.out.println("exercendo oredem: MBR <- MBR * memoria[" + MAR + "] = " + MBR);
            } else if (IR.equals("000110")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                if (memoria[MAR] != 0) {
                    MBR /= memoria[MAR];
                    System.out.println("exercendo oredem: MBR <- MBR / memoria[" + MAR + "] = " + MBR);
                }
            } else if (IR.equals("000111")) {
                PC = Integer.parseInt(aux[1].replace("#", "")) - 1;
                System.out.println("exercendo oredem: PC <- " + (PC + 1) + " (Jump para "
                        + (Integer.parseInt(aux[1].replace("#", "")) - 1) + ")");
            } else if (IR.equals("001000")) {
                if (zero) {
                    PC = Integer.parseInt(aux[1].replace("#", "")) - 1;
                    System.out.println("exercendo oredem: PC <- " + (PC + 1) + " (Jump IF Z para "
                            + (Integer.parseInt(aux[1].replace("#", "")) - 1) + ")");
                }
            } else if (IR.equals("001001")) {
                if (negativo) {
                    PC = Integer.parseInt(aux[1].replace("#", "")) - 1;
                    System.out.println("exercendo oredem: PC <- " + (PC + 1) + " (Jump IF N para "
                            + (Integer.parseInt(aux[1].replace("#", "")) - 1) + ")");
                }
            } else if (IR.equals("001010")) {
                MBR = (int) Math.sqrt(MBR);
                System.out.println("exercendo oredem: MBR <- raiz quadratica(MBR) = " + MBR);
            } else if (IR.equals("001011")) {
                MBR -= MBR;
                System.out.println("exercendo oredem: MBR <- 0");
            } else if (IR.equals("001111")) {
                MAR = Integer.parseInt(aux[1].replace("#", ""));
                memoria[MAR] = MBR;
                System.out.println("exercendo oredem: memoria[" + MAR + "] <- MBR = " + MBR);
            } else if (IR.equals("001100")) {
                System.out.println("exercendo oredem: NOP");
            } else {
                System.out.println("Instrucao desconhecida: " + IR);
            }

            zero = (MBR == 0);
            negativo = (MBR < 0);

            System.out.println("Estado após execução:");
            System.out.println("PC = " + PC);
            System.out.println("MAR = " + MAR);
            System.out.println("MBR = " + MBR);
            System.out.println("IR = " + IR);
            System.out.println();

            if (!IR.startsWith("000111") && !IR.startsWith("001000") && !IR.startsWith("001001")) {
                PC++;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Erro de interrupção: " + e.getMessage());
            }
        }
        System.out.println("Instrucoes executadas.");
    }

    public static void limparTudo() {
        for (int i = 0; i < tamanho_maximo_de_instrucoes; i++) {
            instrucoes[i] = null;
        }
        numInstrucoes = 0;
        PC = 0;
        MBR = 0;
        MAR = 0;
        IR = "";
        zero = false;
        negativo = false;
        for (int i = 0; i < tamanho_maximo_de_memoria; i++) {
            memoria[i] = 0;
        }
    }
}
