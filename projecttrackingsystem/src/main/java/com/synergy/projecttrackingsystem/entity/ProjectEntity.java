package com.synergy.projecttrackingsystem.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    @NotNull
    @Column(unique = true)
    private String projectName;

    @NotNull
    private String status;

}
