package mx.edu.utez.AplicacionDePrincipios.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class AlmacenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private LocalDate fechaRegistro;

    private BigDecimal precioVenta;

    private BigDecimal precioRenta;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(BigDecimal precioRenta) {
        this.precioRenta = precioRenta;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public CedeEntity getCede() {
        return cede;
    }

    public void setCede(CedeEntity cede) {
        this.cede = cede;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "cede_id")
    private CedeEntity cede;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @PrePersist
    private void inicializarFecha() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }

    @PostPersist
    private void generarClave() {
        if (clave == null && cede != null) {
            this.clave = cede.getClave() + "-A" + id;
        }
    }
}