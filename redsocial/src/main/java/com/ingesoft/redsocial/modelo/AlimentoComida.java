package com.ingesoft.redsocial.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class AlimentoComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidadGramos;

    private Integer caloriasCalculadas;

    private Double proteinasCalculadas;

    private Double carbohidratosCalculados;

    private Double grasasCalculadas;

    @ManyToOne
    private Comida comida;

    @ManyToOne
    private Alimento alimento;

    /**
     * Calcula los valores nutricionales basados en la cantidad ingresada
     */
    public void calcularValoresNutricionales() {
        if (alimento != null && cantidadGramos != null) {
            double factor = cantidadGramos / 100.0;
            
            this.caloriasCalculadas = (int) (alimento.getCaloriasPor100g() * factor);
            this.proteinasCalculadas = alimento.getProteinasPor100g() * factor;
            this.carbohidratosCalculados = alimento.getCarbohidratosPor100g() * factor;
            this.grasasCalculadas = alimento.getGrasasPor100g() * factor;
        }
    }

}
