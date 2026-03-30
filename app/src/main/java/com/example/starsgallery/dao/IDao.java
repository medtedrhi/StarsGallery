package com.example.starsgallery.dao; // Déclaration du package DAO (Data Access Object)

import java.util.List; // Importation de l'interface List

public interface IDao<T> { // Déclaration d'une interface générique (T peut être n'importe quel type d'objet)
    boolean create(T o); // Méthode pour créer un objet, retourne vrai si succès
    boolean update(T o); // Méthode pour mettre à jour un objet
    boolean delete(T o); // Méthode pour supprimer un objet
    T findById(int id); // Méthode pour trouver un objet via son ID
    List<T> findAll(); // Méthode pour récupérer la liste de tous les objets
} // Fin de l'interface