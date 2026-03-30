package com.example.starsgallery.ui; // Déclaration du package où se trouve cette classe

import android.content.Intent; // Importation de la classe Intent pour changer d'écran
import android.os.Bundle; // Importation de la classe Bundle pour sauvegarder l'état
import android.widget.ImageView; // Importation de la classe ImageView pour manipuler l'image
import androidx.appcompat.app.AppCompatActivity; // Importation de la classe de base pour les activités Android
import com.example.starsgallery.R; // Importation de la classe R pour accéder aux ressources

public class SplashActivity extends AppCompatActivity { // Déclaration de la classe SplashActivity qui hérite d'AppCompatActivity

    ImageView logo; // Déclaration d'une variable globale pour stocker la référence de l'image

    @Override // Indique que l'on redéfinit la méthode onCreate de la classe parente
    protected void onCreate(Bundle savedInstanceState) { // Méthode appelée lors de la création de l'activité
        super.onCreate(savedInstanceState); // Appel de la méthode onCreate de la classe parente
        setContentView(R.layout.activity_splash); // Lie cette activité au fichier XML layout 'activity_splash'

        logo = findViewById(R.id.logo); // Récupère la vue ImageView depuis le XML grâce à son ID

        logo.animate().rotation(360f).setDuration(2000); // Fait tourner le logo de 360 degrés pendant 2 secondes (2000 ms)
        logo.animate().scaleX(0.5f).scaleY(0.5f).setDuration(3000); // Réduit la taille du logo de moitié sur les axes X et Y pendant 3 secondes
        logo.animate().translationYBy(1000f).setDuration(2000); // Déplace le logo vers le bas de 1000 pixels pendant 2 secondes
        logo.animate().alpha(0f).setDuration(6000); // Rend le logo complètement transparent (0f) progressivement sur 6 secondes

        Thread t = new Thread(() -> { // Création d'un nouveau Thread (processus en arrière-plan)
            try { // Début du bloc try pour capturer les erreurs potentielles
                Thread.sleep(5000); // Met le thread en pause pendant 5 secondes (5000 millisecondes)
                startActivity(new Intent(SplashActivity.this, ListActivity.class)); // Démarre une nouvelle intention pour ouvrir ListActivity
                finish(); // Ferme SplashActivity pour que l'utilisateur ne puisse pas y revenir avec le bouton retour
            } catch (InterruptedException e) { // Capture l'exception si le thread est interrompu
                e.printStackTrace(); // Affiche les détails de l'erreur dans la console
            } // Fin du bloc catch
        }); // Fin de la définition du thread
        t.start(); // Lance l'exécution du thread
    } // Fin de la méthode onCreate
} // Fin de la classe SplashActivity