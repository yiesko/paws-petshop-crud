package xq.yiesko.petshop.util;

/**
 * Utilitários simples para validação de dados fornecidos via console.
 */
public final class ValidationUtils {

    private ValidationUtils() {}

    /**
     * Garante que uma string não seja nula ou vazia.
     *
     * @param value valor avaliado
     * @param message mensagem de erro
     */
    public static void requireNonBlank(
            String value,
            String message
    ) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    }

    /**
     * Garante que um número não seja negativo.
     *
     * @param value   valor avaliado
     * @param message mensagem de erro
     */
    public static void requireNonNegative(
            int value,
            String message
    ) {
        if (value < 0) throw new IllegalArgumentException(message);
    }
}