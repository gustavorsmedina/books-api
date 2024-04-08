package tech.gustavomedina.booksapi.util;

import tech.gustavomedina.booksapi.model.BookGenre;

public class GenreUtil {

    public static boolean isValid(String genreString){

        for (BookGenre genre : BookGenre.values()){
            if(genre.name().equalsIgnoreCase(genreString)) return true;
        }

        return false;
    }

}
