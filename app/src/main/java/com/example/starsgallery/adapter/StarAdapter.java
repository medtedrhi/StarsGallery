package com.example.starsgallery.adapter; // Déclaration du package

import android.app.AlertDialog; // Importation pour la boîte de dialogue popup
import android.content.Context; // Importation du contexte de l'application
import android.content.DialogInterface; // Importation pour gérer les clics des boutons du popup
import android.graphics.Bitmap; // Importation pour gérer les images au format Bitmap
import android.graphics.drawable.BitmapDrawable; // Importation pour extraire le bitmap d'une vue
import android.view.LayoutInflater; // Importation pour transformer le XML en vues Java
import android.view.View; // Importation de la classe View de base
import android.view.ViewGroup; // Importation de ViewGroup (conteneur de vues)
import android.widget.Filter; // Importation de la classe Filter pour la recherche
import android.widget.Filterable; // Importation de l'interface Filterable
import android.widget.ImageView; // Importation d'ImageView
import android.widget.RatingBar; // Importation de RatingBar
import android.widget.TextView; // Importation de TextView
import androidx.annotation.NonNull; // Annotation de sécurité (non nul)
import androidx.recyclerview.widget.RecyclerView; // Importation de RecyclerView
import com.bumptech.glide.Glide; // Importation de la librairie Glide pour les images internet
import com.bumptech.glide.request.RequestOptions; // Options spécifiques pour Glide
import com.example.starsgallery.R; // Importation des ressources
import com.example.starsgallery.beans.Star; // Importation du modèle
import com.example.starsgallery.service.StarService; // Importation du service
import java.util.ArrayList; // Importation ArrayList
import java.util.List; // Importation List

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable { // L'Adapter étend RecyclerView et implémente Filterable pour la recherche

    private List<Star> stars; // Liste originale contenant toutes les stars
    private List<Star> starsFilter; // Liste contenant uniquement les stars filtrées après recherche
    private Context context; // Contexte de l'activité (nécessaire pour Glide et AlertDialog)
    private NewFilter mfilter; // Instance de notre classe de filtrage personnalisée

    public StarAdapter(Context context, List<Star> stars) { // Constructeur
        this.context = context; // Assigne le contexte
        this.stars = stars; // Assigne la liste originale
        this.starsFilter = new ArrayList<>(stars); // Initialise la liste filtrée avec tous les éléments au début
        this.mfilter = new NewFilter(this); // Initialise l'objet de filtrage
    } // Fin constructeur

    @NonNull // Indique que le retour ne sera pas nul
    @Override // Redéfinition de méthode
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Création d'une nouvelle "carte"
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false); // Transforme star_item.xml en objet View Java
        final StarViewHolder holder = new StarViewHolder(v); // Crée un ViewHolder pour stocker les références aux sous-vues

        holder.itemView.setOnClickListener(new View.OnClickListener() { // Ajoute un écouteur de clic sur toute la ligne (Étape 10)
            @Override // Redéfinition de l'action onClick
            public void onClick(View v) { // Quand l'utilisateur clique sur une star
                View popup = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null, false); // Charge le design du popup

                final ImageView img = popup.findViewById(R.id.img); // Récupère l'image dans le popup
                final RatingBar bar = popup.findViewById(R.id.ratingBar); // Récupère la barre dans le popup
                final TextView idss = popup.findViewById(R.id.idss); // Récupère le champ texte ID dans le popup

                // Récupération de l'image de la ligne cliquée pour l'injecter dans le popup
                Bitmap bitmap = ((BitmapDrawable) ((ImageView) v.findViewById(R.id.imgStar)).getDrawable()).getBitmap(); // Extrait les pixels de l'image cliquée
                img.setImageBitmap(bitmap); // Place cette image dans le popup

                bar.setRating(((RatingBar) v.findViewById(R.id.rating)).getRating()); // Copie la note actuelle vers le popup
                idss.setText(((TextView) v.findViewById(R.id.ids)).getText().toString()); // Copie l'ID caché vers le popup

                AlertDialog dialog = new AlertDialog.Builder(context) // Construction de la fenêtre d'alerte
                        .setTitle("Notez :") // Ajoute un titre au popup
                        .setMessage("Donner une note entre 1 et 5 :") // Ajoute une consigne
                        .setView(popup) // Intègre notre design de popup personnalisé
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() { // Configure le bouton "Valider"
                            @Override // Action au clic sur valider
                            public void onClick(DialogInterface dialog, int which) { // Méthode exécutée
                                float s = bar.getRating(); // Récupère la nouvelle note donnée par l'utilisateur
                                int ids = Integer.parseInt(idss.getText().toString()); // Récupère l'ID en format entier

                                Star star = StarService.getInstance().findById(ids); // Cherche l'objet Star correspondant dans la base
                                star.setRating(s); // Modifie la note de l'objet
                                StarService.getInstance().update(star); // Sauvegarde la modification dans le Service
                                notifyItemChanged(holder.getAdapterPosition()); // Demande au RecyclerView de rafraîchir cette ligne visuellement
                            } // Fin de la méthode onClick Valider
                        }) // Fin setPositiveButton
                        .setNegativeButton("Annuler", null) // Configure le bouton "Annuler" qui ferme juste la fenêtre (null = pas d'action spéciale)
                        .create(); // Construit la boîte de dialogue finale
                dialog.show(); // Affiche la boîte de dialogue à l'écran
            } // Fin onClick de l'item
        }); // Fin setOnClickListener

        return holder; // Retourne la vue construite
    } // Fin onCreateViewHolder

    @Override // Redéfinition
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) { // Remplit une carte avec les données
        Glide.with(context) // Utilise Glide
                .asBitmap() // Format image Bitmap
                .load(starsFilter.get(position).getImg()) // Charge l'URL de l'image depuis la liste filtrée
                .apply(new RequestOptions().override(100, 100)) // Redimensionne l'image pour optimiser la mémoire
                .into(holder.img); // Place l'image téléchargée dans l'ImageView

        holder.name.setText(starsFilter.get(position).getName().toUpperCase()); // Écrit le nom en majuscules
        holder.rating.setRating(starsFilter.get(position).getRating()); // Applique la note
        holder.idss.setText(String.valueOf(starsFilter.get(position).getId())); // Stocke l'ID dans le champ invisible
    } // Fin onBindViewHolder

    @Override // Redéfinition
    public int getItemCount() { // Compte le nombre d'éléments
        return starsFilter.size(); // Retourne la taille de la liste actuellement filtrée
    } // Fin getItemCount

    @Override // Redéfinition
    public Filter getFilter() { // Retourne l'outil de filtrage quand la SearchView est utilisée
        return mfilter; // Retourne notre classe NewFilter
    } // Fin getFilter

    public class StarViewHolder extends RecyclerView.ViewHolder { // Classe interne représentant les vues d'une carte
        TextView idss, name; // Déclaration des TextViews
        ImageView img; // Déclaration de l'ImageView
        RatingBar rating; // Déclaration de la RatingBar

        public StarViewHolder(@NonNull View itemView) { // Constructeur
            super(itemView); // Appel au constructeur parent
            idss = itemView.findViewById(R.id.ids); // Lie le code à l'ID XML
            img = itemView.findViewById(R.id.imgStar); // Lie le code à l'image XML
            name = itemView.findViewById(R.id.tvName); // Lie le code au nom XML
            rating = itemView.findViewById(R.id.rating); // Lie le code à la note XML
        } // Fin constructeur StarViewHolder
    } // Fin classe StarViewHolder

    public class NewFilter extends Filter { // Classe interne gérant la logique de recherche
        public RecyclerView.Adapter mAdapter; // Référence à l'Adapter principal

        public NewFilter(RecyclerView.Adapter mAdapter) { // Constructeur
            this.mAdapter = mAdapter; // Assignation
        } // Fin constructeur NewFilter

        @Override // Redéfinition
        protected FilterResults performFiltering(CharSequence charSequence) { // Logique exécutée en arrière-plan pendant la saisie
            List<Star> filtered = new ArrayList<>(); // Nouvelle liste temporaire pour stocker les résultats
            if (charSequence == null || charSequence.length() == 0) { // Si le champ de recherche est vide
                filtered.addAll(stars); // On remet toutes les stars d'origine
            } else { // Si l'utilisateur a tapé quelque chose
                String filterPattern = charSequence.toString().toLowerCase().trim(); // Convertit la recherche en minuscules sans espaces
                for (Star p : stars) { // Parcourt la base de données entière
                    if (p.getName().toLowerCase().startsWith(filterPattern)) { // Si le nom de la star commence par ce qui est tapé
                        filtered.add(p); // On l'ajoute aux résultats
                    } // Fin du if
                } // Fin de la boucle
            } // Fin du else
            FilterResults results = new FilterResults(); // Objet pour encapsuler le résultat final
            results.values = filtered; // On y place notre liste temporaire
            results.count = filtered.size(); // On compte combien ont été trouvées
            return results; // Retourne le résultat
        } // Fin performFiltering

        @Override // Redéfinition
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) { // Met à jour l'interface
            starsFilter = (List<Star>) filterResults.values; // Remplace la liste affichée par la nouvelle liste filtrée
            mAdapter.notifyDataSetChanged(); // Indique au RecyclerView de tout redessiner avec les nouvelles données
        } // Fin publishResults
    } // Fin classe NewFilter
} // Fin classe StarAdapter