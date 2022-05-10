<template>
  <div>
    <Header></Header>
    <hr class="m-0">
    <main class="bg-main">
      <div class="container-md pt-5 pb-5">
        <div class="row justify-content-center">
          <div class="col-5 bg-light shadow-lg" style="border-radius: 25px">
            <div class="mx-auto my-5">
              <h2 class="display-4 fw-normal text-light">
                Start Listening Today!
              </h2>
              <p class="lead fw-normal">
                Join now and explore whole new world of music!
              </p>
              <div class="d-inline-flex flex-column">
                <router-link v-bind:to="{name: 'u-register'}"
                             class="btn btn-light mb-4">
                  Register
                </router-link>
                <a v-on:click="login_user"
                   class="btn btn-outline-light" href="#">
                  Login
                </a>
              </div>
            </div>
          </div>
          <div class="col-1"></div>
          <div class="col-5 bg-dark shadow-lg" style="border-radius: 25px">
            <div class="mx-auto my-5">
              <h2 class="display-4 fw-normal text-light">
                Professional Artist?
              </h2>
              <p class="lead fw-normal">
                Join our platform and show your creations to the world!
              </p>
              <div class="d-inline-flex flex-column">
                <router-link v-bind:to="{name: 'a-register'}"
                             class="btn btn-light mb-3">
                  Apply
                </router-link>
                <a v-on:click="login_artist"
                   class="btn btn-outline-light"
                   href="#">
                  Login
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
    <hr class="m-0">
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '@/components/public/Header'
import Footer from '@/components/public/Footer'

export default {
  name: 'Index',
  components: {
    Header,
    Footer
  },
  methods: {
    login_user() {
      this.$auth_user.loginWithRedirect({
        redirect_uri: "http://localhost:8080/user"
      })
    },
    login_artist() {
      this.$auth_artist.loginWithRedirect({
        redirect_uri: "http://localhost:8080/pro"
      })
    },
  }
}
</script>

<style lang="scss" scoped>

.bg-main {
  background: rgb(52,184,213);
  background: radial-gradient(circle, rgba(52,184,213,1) 0%, rgba(35,70,117,1) 100%);
}

.bg-light {
  background:
      linear-gradient(217deg, rgb(46, 43, 69), rgba(255,0,0,0) 70.71%),
      linear-gradient(127deg, rgb(84, 208, 159), rgba(0,255,0,0) 70.71%),
      linear-gradient(336deg, rgb(13, 49, 168), rgba(0,0,255,0) 70.71%);
}

.bg-dark {
  background: rgb(18,18,22);
  background:
      linear-gradient(225deg, rgba(18,18,22,1) 0%, rgb(46, 43, 69, 0.7) 100%),
      linear-gradient(135deg, rgba(18,18,22,1) 0%, rgb(45, 63, 80) 100%);
}

</style>