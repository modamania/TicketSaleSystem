package ru.tersoft.ticketsale.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "attractions")
@ApiModel(value = "Attraction")
public class Attraction implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(required = true)
    private String name;

    @Column(name = "description", columnDefinition="TEXT", nullable = false)
    @ApiModelProperty(required = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "price", columnDefinition = "decimal(19,4)", nullable = false)
    @ApiModelProperty(required = true)
    private Float price;

    @Column(name = "imagepath")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imagepath;

    @Column(name = "smallimagepath")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String smallimagepath;

    @OneToOne
    private Maintenance maintenance;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getSmallimagepath() {
        return smallimagepath;
    }

    public void setSmallimagepath(String smallimagepath) {
        this.smallimagepath = smallimagepath;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }
}