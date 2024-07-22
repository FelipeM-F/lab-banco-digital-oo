public class Cliente {

	private String nome;
	private TipoCliente tipoCliente;
	private String cpf;
	private String cnpj;

	public enum TipoCliente {
		PESSOA_JURIDICA, PESSOA_FISICA
	}

	public Cliente(String nome, TipoCliente tipoCliente, String cpfOuCnpj) {
		this.nome = nome;
		this.tipoCliente = tipoCliente;

		if (tipoCliente == TipoCliente.PESSOA_FISICA) {
			if (cpfOuCnpj == null || !isValidCPF(cpfOuCnpj)) {
				throw new IllegalArgumentException("CPF inválido.");
			}
			this.cpf = cpfOuCnpj;
		} else if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
			if (cpfOuCnpj == null || !isValidCNPJ(cpfOuCnpj)) {
				throw new IllegalArgumentException("CNPJ inválido.");
			}
			this.cnpj = cpfOuCnpj;
		}
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		if (tipoCliente == TipoCliente.PESSOA_FISICA) {
			return cpf;
		}
		return null;
	}

	public String getCnpj() {
		if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
			return cnpj;
		}
		return null;
	}

	// Validação de CPF
	private boolean isValidCPF(String cpf) {
		// Remover pontuação
		cpf = cpf.replaceAll("\\D", "");
		// Verificar tamanho
		if (cpf.length() != 11) {
			return false;
		}

		// Verificar se todos os dígitos são iguais
		if (cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		// Calcular dígitos verificadores
		int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * pesos[i];
		}

		int primeiroDigitoVerificador = 11 - (soma % 11);
		if (primeiroDigitoVerificador >= 10) {
			primeiroDigitoVerificador = 0;
		}

		if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigitoVerificador) {
			return false;
		}

		pesos = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * pesos[i];
		}

		int segundoDigitoVerificador = 11 - (soma % 11);
		if (segundoDigitoVerificador >= 10) {
			segundoDigitoVerificador = 0;
		}

		return Character.getNumericValue(cpf.charAt(10)) == segundoDigitoVerificador;
	}

	// Validação de CNPJ
	private boolean isValidCNPJ(String cnpj) {
		// Remover pontuação
		cnpj = cnpj.replaceAll("\\D", "");

		// Verificar tamanho
		if (cnpj.length() != 14) {
			return false;
		}

		// Calcular dígitos verificadores
		int[] pesos = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		int soma = 0;
		for (int i = 0; i < 12; i++) {
			soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
		}

		int primeiroDigitoVerificador = 11 - (soma % 11);
		if (primeiroDigitoVerificador >= 10) {
			primeiroDigitoVerificador = 0;
		}

		if (Character.getNumericValue(cnpj.charAt(12)) != primeiroDigitoVerificador) {
			return false;
		}

		pesos = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		soma = 0;
		for (int i = 0; i < 13; i++) {
			soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
		}

		int segundoDigitoVerificador = 11 - (soma % 11);
		if (segundoDigitoVerificador >= 10) {
			segundoDigitoVerificador = 0;
		}

		return Character.getNumericValue(cnpj.charAt(13)) == segundoDigitoVerificador;
	}
}