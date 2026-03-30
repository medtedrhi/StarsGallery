package com.example.starsgallery.service; // Package service pour la logique métier

import com.example.starsgallery.R; // Importation des ressources
import com.example.starsgallery.beans.Star; // Importation de la classe Star
import com.example.starsgallery.dao.IDao; // Importation de l'interface IDao
import java.util.ArrayList; // Importation de ArrayList
import java.util.List; // Importation de List

public class StarService implements IDao<Star> { // Implémentation de l'interface IDao pour l'objet Star
    private List<Star> stars; // Liste qui va stocker toutes nos stars en mémoire
    private static StarService instance; // Instance statique pour le pattern Singleton

    private StarService() { // Constructeur privé (empêche la création d'instances avec 'new' depuis l'extérieur)
        stars = new ArrayList<>(); // Initialise la liste vide
        seed(); // Appelle la méthode pour populer la liste avec des données de test
    } // Fin du constructeur

    public static StarService getInstance() { // Méthode publique pour récupérer l'unique instance
        if (instance == null) instance = new StarService(); // Si l'instance n'existe pas, on la crée
        return instance; // On retourne l'instance unique
    } // Fin de la méthode getInstance

    private void seed() { // Méthode pour injecter des données locales au démarrage
        // Utilisation des identifiants de ressources mipmap
        stars.add(new Star("Emma Watson", R.mipmap.emma, 4.5f));
        stars.add(new Star("Tom Cruise", R.mipmap.tom, 4.2f));
        stars.add(new Star("Scarlett Johansson", R.mipmap.scarlett, 4.7f));
        stars.add(new Star("Leonardo DiCaprio", R.mipmap.leonardo, 4.8f));
    } // Fin de la méthode seed

    @Override public boolean create(Star o) { return stars.add(o); } // Ajoute l'objet à la liste

    @Override public boolean update(Star o) { // Met à jour une star existante
        for (Star s : stars) { // Parcourt toutes les stars de la liste
            if (s.getId() == o.getId()) { // Si on trouve la star avec le même ID
                s.setName(o.getName()); // On met à jour son nom
                s.setImg(o.getImg()); // On met à jour son identifiant d'image
                s.setRating(o.getRating()); // On met à jour sa note
                return true; // Retourne vrai car la mise à jour a réussi
            } // Fin du if
        } // Fin de la boucle
        return false; // Si non trouvé, retourne faux
    } // Fin de la méthode update

    @Override public boolean delete(Star o) { return stars.remove(o); } // Supprime la star de la liste

    @Override public Star findById(int id) { // Cherche une star par son ID
        for (Star s : stars) if (s.getId() == id) return s; // Parcourt et retourne la star correspondante
        return null; // Si aucune correspondance, retourne null
    } // Fin de findById

    @Override public List<Star> findAll() { return stars; } // Retourne la liste complète
} // Fin de la classe