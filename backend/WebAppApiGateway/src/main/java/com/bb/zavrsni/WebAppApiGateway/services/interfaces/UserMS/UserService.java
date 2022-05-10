package com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.User.RatingDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserDto;

/**
 * Interface for communication with User Management Micro Service.
 */
public interface UserService {

    /**
     * Create new User.
     *
     * @param userCreationDto User creation data
     * @return Newly created User object
     */
    UserDto createNewUser(UserCreationDto userCreationDto);

    /**
     * Get User by ID.
     *
     * @param id User ID
     * @return User object
     */
    UserDto getUserById(String id);

    /**
     * Get User by Auth0 ID.
     *
     * @param id User Auth0 ID
     * @return User object
     */
    UserDto getUserByAuth0Id(String id);

    /**
     * Get User by username.
     *
     * @param username User username
     * @return User object
     */
    UserDto getUserByUsername(String username);

    enum FavouriteWhat {
        ALBUM("album"),
        ARTIST("artist"),
        GENRE("genre"),
        PLAYLIST("playlist"),
        TRACK("track");

        private final String value;

        public String getValue() {
            return this.value;
        }

        FavouriteWhat(String value) {
            this.value = value;
        }
    }

    /**
     * Add specified content to Users favourites list.<br>
     * Content type is one of values in provided enum <i>FavouriteWhat<i/>.
     *
     * @param what Content type
     * @param userId User ID
     * @param id ID of the provided content
     */
    void addToFavourites(String what, String userId, String id);

    /**
     * Remove specified content from Users favourites list.
     * Content type is one of values in provided enum <i>FavouriteWhat<i/>.
     *
     * @param what Content type
     * @param userId User ID
     * @param id ID of provided content
     */
    void removeFromFavourites(String what, String userId, String id);

    enum RateWhat {
        ALBUM("album"),
        TRACK("track");

        private final String value;

        public String getValue() {
            return this.value;
        }

        RateWhat(String value) {
            this.value = value;
        }
    }

    void addRatingFor(String what, String userId, String id, RatingDto ratingDto);

    void removeRatingFrom(String what, String userId, String id);

}
