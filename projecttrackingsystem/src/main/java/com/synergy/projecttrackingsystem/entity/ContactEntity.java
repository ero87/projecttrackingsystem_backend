package com.synergy.projecttrackingsystem.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "contact")
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    @NotNull
    private String contactFullName;

    @NotNull
    @Column(unique = true)
    private String email;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEntity projectEntity;

}
