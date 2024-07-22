public class ContaCorrente extends Conta {

    private double limite;
    private double limiteUtilizado;

    public ContaCorrente(Cliente cliente, double limite) {
        super(cliente);
        this.limite = limite;
        this.limiteUtilizado = 0;
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirInfosComuns();
        System.out.println("Limite disponível: " + getLimiteDisponivel());
        System.out.println("Limite utilizado: " + limiteUtilizado);
    }

    @Override
    public boolean sacar(double valor) {
        double saldoDisponivel = saldo + limite - limiteUtilizado;
        if (valor <= saldoDisponivel) {
            if (valor <= saldo) {
                saldo -= valor;
            } else {
                double valorFaltante = valor - saldo;
                saldo = 0;
                limiteUtilizado += valorFaltante;
            }
            return true;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    @Override
    public void depositar(double valor) {
        if (limiteUtilizado > 0) {
            double valorParaCobrirLimite = Math.min(valor, limiteUtilizado);
            limiteUtilizado -= valorParaCobrirLimite;
            valor -= valorParaCobrirLimite;
        }
        saldo += valor;
    }

    @Override
    public boolean transferir(double valor, IConta contaDestino) {
        if (this.sacar(valor)) {
            contaDestino.depositar(valor);
            return true;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    public double getLimiteDisponivel() {
        return limite - limiteUtilizado;
    }
}
