package valeron.bondar.services;

import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicles;

import java.io.UnsupportedEncodingException;

public interface SearchNameService {
    Vehicles searchByName(String query, String startIndex, String maxResults, String sortField, String orderStr) throws ResponseException, UnsupportedEncodingException;
}
