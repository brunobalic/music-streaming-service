import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: "http://localhost:8100/api/v1",
  headers: {
    "Content-type": "application/json"
  }
})

class BackendService {

  getCurrentUser(accessToken) {
    return axiosInstance
      .get("/users", {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getArtistData(artistId, accessToken) {
    return axiosInstance
      .get("/artists/" + artistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getTop50TracksFromArtist(artistId, accessToken) {
    return axiosInstance
      .get("/music/stats/top50/" + artistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        },
      })
  }

  getTop100Global(accessToken) {
    return axiosInstance
      .get("/music/stats/global/top100", {
        headers: {
          Authorization: 'Bearer ' + accessToken
        },
      })
  }

  searchAllContent(query, accessToken) {
    return axiosInstance
      .get("/search", {
        headers: {
          Authorization: 'Bearer ' + accessToken
        },
        params: {
          q: query
        }
      })
  }

  searchSpecificContent(query, type, page, pageSize , accessToken) {
    return axiosInstance
      .get("/search/" + type, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        },
        params: {
          q: query,
          p: page,
          s: pageSize
        }
      })
  }

  getAlbumData(albumId, accessToken) {
    console.log(accessToken, "accessToken - na pocetku getAlbumData()")
    return axiosInstance
      .get("/albums/" + albumId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getAllAlbumsFromArtist(artistId, accessToken) {
    return axiosInstance
      .get("/albums/artist/" + artistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getPlaylistData(playlistId, accessToken) {
    return axiosInstance
      .get("/playlists/" + playlistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getAllPlaylistsFromCurrentUser(accessToken) {
    return axiosInstance
      .get("/playlists/user", {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  addTrackToPlaylist(trackId, playlistId, accessToken) {
    return axiosInstance
      .post("/playlists/" + playlistId + "/tracks/" + trackId, undefined, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  removeTrackFromPlaylist(trackId, playlistId, accessToken) {
    return axiosInstance
      .delete("/playlists/" + playlistId + "/tracks/" + trackId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  createPlaylist(playlistName, playlistDescription, accessToken) {
    let data = {
      name: playlistName,
      description: playlistDescription
    }
    return axiosInstance
      .post("/playlists", data, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  deletePlaylist(playlistId, accessToken) {
    return axiosInstance
      .delete("/playlists/" + playlistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getTrack(trackId, accessToken) {
    return axiosInstance
      .get("/tracks/" + trackId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getPlayHistory(accessToken) {
    return axiosInstance
      .get("/play-history", {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  //
  // FAVOURITES
  //

  addTrackToFav(trackId, accessToken) {
    return axiosInstance
      .post("/users/fav/track/" + trackId, undefined,{
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  removeTrackFromFav(trackId, accessToken) {
    return axiosInstance
      .delete("/users/fav/track/" + trackId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  //

  addAlbumToFav(albumId, accessToken) {
    return axiosInstance
      .post("/users/fav/album/" + albumId, undefined, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  removeAlbumFromFav(albumId, accessToken) {
    return axiosInstance
      .delete("/users/fav/album/" + albumId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  //

  addPlaylistToFav(playlistId, accessToken) {
    return axiosInstance
      .post("/users/fav/playlist/" + playlistId, undefined, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  removePlaylistFromFav(playlistId, accessToken) {
    return axiosInstance
      .delete("/users/fav/playlist/" + playlistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  //

  addArtistToFav(artistId, accessToken) {
    return axiosInstance
      .post("/users/fav/artist/" + artistId, undefined, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  removeArtistFromFav(artistId, accessToken) {
    return axiosInstance
      .delete("/users/fav/artist/" + artistId, {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

}

export default new BackendService()
