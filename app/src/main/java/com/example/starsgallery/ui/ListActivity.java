package com.example.starsgallery.ui; // Déclaration du package

import android.os.Bundle; // Importation pour sauvegarder l'état
import android.util.Log; // Importation pour afficher des messages dans la console Logcat
import android.view.Menu; // Importation pour manipuler les menus
import android.view.MenuItem; // Importation pour gérer le clic sur un item de menu
import androidx.appcompat.app.AppCompatActivity; // Classe de base des activités
import androidx.appcompat.widget.SearchView; // Importation de la barre de recherche
import androidx.core.app.ShareCompat; // Importation de l'outil pour le partage facile
import androidx.core.view.MenuItemCompat; // Importation pour assurer la compatibilité des menus
import androidx.recyclerview.widget.LinearLayoutManager; // Gestionnaire de liste linéaire
import androidx.recyclerview.widget.RecyclerView; // Importation RecyclerView
import com.example.starsgallery.R; // Ressources
import com.example.starsgallery.adapter.StarAdapter; // Adapter personnalisé
import com.example.starsgallery.service.StarService; // Données

public class ListActivity extends AppCompatActivity { // Classe d'affichage de la liste

    private StarAdapter starAdapter; // Déclaration de l'adapter au niveau de la classe pour y accéder partout

    @Override // Redéfinition
    protected void onCreate(Bundle savedInstanceState) { // Méthode initiale
        super.onCreate(savedInstanceState); // Appel parent
        setContentView(R.layout.activity_list); // Lancement de l'interface associée

        RecyclerView rv = findViewById(R.id.recycle_view); // Récupération du RecyclerView via le XML
        rv.setLayoutManager(new LinearLayoutManager(this)); // Indique qu'on veut afficher les éléments de haut en bas

        // Initialisation de l'adapter avec le contexte et les données de la base
        starAdapter = new StarAdapter(this, StarService.getInstance().findAll());
        rv.setAdapter(starAdapter); // Attache l'adapter au RecyclerView pour l'affichage
    } // Fin de onCreate

    @Override // Redéfinition
    public boolean onCreateOptionsMenu(Menu menu) { // Méthode appelée pour créer le menu en haut à droite
        getMenuInflater().inflate(R.menu.menu, menu); // Charge le fichier menu.xml dans la barre supérieure
        MenuItem menuItem = menu.findItem(R.id.app_bar_search); // Trouve l'élément Loupe
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem); // Convertit l'élément en objet SearchView

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // Attache un écouteur de frappe
            @Override // Action quand l'utilisateur appuie sur "Entrée/Valider"
            public boolean onQueryTextSubmit(String query) { // Méthode de soumission
                return true; // Retourne vrai pour indiquer que c'est géré
            } // Fin onQueryTextSubmit

            @Override // Action à CHAQUE nouvelle lettre tapée ou effacée
            public boolean onQueryTextChange(String newText) { // Méthode de changement en direct
                if (starAdapter != null) { // Vérifie par sécurité que l'adapter existe bien
                    starAdapter.getFilter().filter(newText); // Envoie le texte tapé au filtre de notre StarAdapter
                } // Fin vérification
                return true; // Retourne vrai
            } // Fin onQueryTextChange
        }); // Fin setOnQueryTextListener
        return true; // Affiche le menu
    } // Fin onCreateOptionsMenu

    @Override // Redéfinition
    public boolean onOptionsItemSelected(MenuItem item) { // Méthode appelée quand on clique sur un bouton du menu
        if (item.getItemId() == R.id.share) { // Si l'ID du bouton cliqué est "share" (Partager)
            String txt = "Stars"; // Le message texte que l'on veut partager
            String mimeType = "text/plain"; // Type de fichier (ici du texte brut)

            ShareCompat.IntentBuilder // Utilisation de l'outil AndroidX pour générer la fenêtre de partage
                    .from(this) // Indique d'où on lance le partage
                    .setType(mimeType) // Spécifie que c'est du texte
                    .setChooserTitle("Partager l’application Stars") // Titre de la petite fenêtre Android listant les applications (WhatsApp, etc.)
                    .setText(txt) // Attache le texte à partager
                    .startChooser(); // Affiche la fenêtre à l'écran
        } // Fin if
        return super.onOptionsItemSelected(item); // Comportement par défaut pour les autres boutons
    } // Fin onOptionsItemSelected
} // Fin classe ListActivity