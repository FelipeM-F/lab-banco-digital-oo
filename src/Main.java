public class Main {

    public static void main(String[] args) {

        // Criando clientes válidos
        Cliente clientePF = new Cliente("João Silva", Cliente.TipoCliente.PESSOA_FISICA, "12345678909");
        Cliente clientePJ = new Cliente("Empresa XYZ", Cliente.TipoCliente.PESSOA_JURIDICA, "12345678000195");

        // Criando contas
        ContaCorrente contaCorrentePF = new ContaCorrente(clientePF, 1000.00);
        ContaCorrente contaCorrentePJ = new ContaCorrente(clientePJ, 5000.00);
        ContaPoupanca contaPoupancaPF = new ContaPoupanca(clientePF);

        // Testando operações na conta corrente de pessoa física
        System.out.println("Operações na conta corrente de pessoa física:");
        contaCorrentePF.depositar(500.00);
        contaCorrentePF.imprimirExtrato();

        try {
            contaCorrentePF.sacar(600.00);
            contaCorrentePF.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            contaCorrentePF.sacar(2000.00); // Excedendo o limite
            contaCorrentePF.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }

        contaCorrentePF.depositar(200.00);
        contaCorrentePF.imprimirExtrato();

        // Testando transferência entre contas
        System.out.println(
                "\nTransferência da conta corrente de pessoa física para a conta corrente de pessoa jurídica:");
        try {
            contaCorrentePF.transferir(300.00, contaCorrentePJ);
            contaCorrentePF.imprimirExtrato();
            contaCorrentePJ.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao transferir: " + e.getMessage());
        }

        // Testando transferência com saldo insuficiente
        System.out.println("\nTentativa de transferência com saldo insuficiente:");
        try {
            contaCorrentePF.transferir(1000.00, contaCorrentePJ);
            contaCorrentePF.imprimirExtrato();
            contaCorrentePJ.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao transferir: " + e.getMessage());
        }

        // Testando operações na conta poupança de pessoa física
        System.out.println("\nOperações na conta poupança de pessoa física:");
        contaPoupancaPF.depositar(200.00);
        contaPoupancaPF.imprimirExtrato();

        try {
            contaPoupancaPF.sacar(100.00);
            contaPoupancaPF.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            contaPoupancaPF.sacar(300.00); // Tentativa de saque com saldo insuficiente
            contaPoupancaPF.imprimirExtrato();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }

        // Teste com CPF inválido
        try {
            Cliente clienteInvalidoCPF = new Cliente("Carlos Souza", Cliente.TipoCliente.PESSOA_FISICA, "12345678900");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }

        // Teste com CNPJ inválido
        try {
            Cliente clienteInvalidoCNPJ = new Cliente("Empresa ABC", Cliente.TipoCliente.PESSOA_JURIDICA,
                    "12345678000100");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }
}
