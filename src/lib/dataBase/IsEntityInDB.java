package lib.dataBase;

import java.util.HashMap;

/**
 * Interface que todas as classes que representam entidades do banco de dados devem implementar
 */
public interface IsEntityInDB{
    /**
     * Metodo para recuperar qual o nome da entidade que a classe representa
     *
     * @return String com o nome da Entidade
     */
    String getEntityName();

    /**
     * Metodo para recuperar o nome e o valor de todos os campos que não tem valor nulo da entidade
     * @return Um HashMap de String com todos os nomes e valores (nomes usados como chave)
     */
    HashMap<String, String> getNotNullFields();

    /**
     * Metodo para serializar um objeto dessa classe em json
     * @return o json
     */
    String toJson();

    /**
     * Metodo para setar os valores nesse objeto apartir de um json
     * @param json a ser recuperado os valore
     */
    void setFromJson(String json);
}