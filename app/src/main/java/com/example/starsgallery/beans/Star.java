package com.example.starsgallery.beans; // Déclaration du package pour les modèles de données

public class Star { // Déclaration de la classe publique représentant une Star
    private int id; // Identifiant unique de la star
    private String name; // Nom de la star
    private int img; // Identifiant de la ressource image (R.mipmap.xxx)
    private float rating; // Note attribuée à la star
    private static int counter = 0; // Compteur statique partagé par toutes les instances pour générer des IDs uniques

    public Star(String name, int img, float rating) { // Constructeur avec paramètres
        this.id = ++counter; // Incrémente le compteur et assigne cette valeur comme ID
        this.name = name; // Initialise le nom avec la valeur passée en paramètre
        this.img = img; // Initialise l'identifiant de l'image
        this.rating = rating; // Initialise la note
    } // Fin du constructeur

    public int getId() { return id; } // Méthode pour récupérer l'ID
    public String getName() { return name; } // Méthode pour récupérer le nom
    public int getImg() { return img; } // Méthode pour récupérer l'identifiant de l'image
    public float getRating() { return rating; } // Méthode pour récupérer la note

    public void setName(String name) { this.name = name; } // Méthode pour modifier le nom
    public void setImg(int img) { this.img = img; } // Méthode pour modifier l'image
    public void setRating(float rating) { this.rating = rating; } // Méthode pour modifier la note
} // Fin de la classe