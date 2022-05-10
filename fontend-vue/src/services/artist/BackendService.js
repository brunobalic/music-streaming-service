import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: "http://localhost:8100/api/v1"
})

class BackendService {

  getArtistData(accessToken) {
    return axiosInstance
      .get('/pro/artist', {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  createCollection(formData, accessToken) {
    return axiosInstance
      .post('/pro/artist/albums',
        formData,{
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + accessToken
          }
        })
  }

  uploadTrack(formData, accessToken) {
    return axiosInstance
      .post('/pro/artist/tracks',
        formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + accessToken,
          }
        })
  }

  getAllArtistAlbums(accessToken) {
    console.log(accessToken)
    return axiosInstance
      .get('/pro/artist/albums', {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

  getPlayStats(accessToken) {
    return axiosInstance
      .get('/music/stats', {
        headers: {
          Authorization: 'Bearer ' + accessToken
        }
      })
  }

}

export default new BackendService()
