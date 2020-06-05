package br.com.climb.test.model;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Column;
import br.com.climb.core.mapping.Entity;

@Entity(name = "tb_cliente")
public class Cliente extends PersistentEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "idade")
    private Long idade;

    @Column(name = "altura")
    private Float altura;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "casado")
    private Boolean casado;

    public void setCasado(Boolean casado) {
        this.casado = casado;
    }

    public Boolean getCasado() {
        return casado;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdade() {
        return idade;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getAltura() {
        return altura;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPeso() {
        return peso;
    }

    public void setIdade(Long idade) {
        this.idade = idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                " id=" + getId() +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", altura=" + altura +
                ", peso=" + peso +
                ", casado=" + casado +
                '}';
    }
}
