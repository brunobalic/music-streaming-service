<template>
  <div>
    <Header></Header>
    <div class="container text-white">
      <div class="py-5">
        <h2>Create Your Account</h2>
        <p class="mb-0 text-muted">(Regular user)</p>
      </div>
      <div class="row g-5 justify-content-center"
           v-bind:class="{'loading': loading}">
        <div class="col-md-7 col-lg-8">
          <form class="needs-validation" novalidate="">
            <div class="row g-3">
              <div class="input-group mb-3">
                <span class="input-group-text">First name</span>
                <input v-model='firstName'
                       type="text" class="form-control" placeholder="First name" aria-label="First name">
                <span class="input-group-text">* Last name</span>
                <input v-model="lastName"
                       type="text" class="form-control" placeholder="Last name (optional)" aria-label="Last name">
              </div>
              <div class="my-0">
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.firstName.required">
                  &bull; First name is required
                </p>
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.firstName.minLength">
                  &bull; First name must have at least {{$v.firstName.$params.minLength.min}} letters.
                </p>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text" id="input-username">Username</span>
                <input v-model="username"
                       type="text" class="form-control" placeholder="username" aria-label="Username" aria-describedby="input-username">
              </div>
              <div class="my-0">
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.username.required">&bull; Username is required</p>
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.username.minLength">
                  &bull; Username must have at least {{$v.username.$params.minLength.min}} letters.
                </p>
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.username.maxLength">
                  &bull; Username cannot have more that {{$v.username.$params.maxLength.max}} letters.
                </p>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text" id="input-email">Email</span>
                <input v-model="email"
                       type="email" class="form-control" placeholder="you@example.com" aria-label="Email" aria-describedby="input-email">
              </div>
              <div class="my-0">
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.email.required">&bull; Email is required
                </p>
                <p class="my-0 ps-2 text-start text-warning"
                   v-if="!$v.email.email">&bull; Enter valid email
                </p>
              </div>

              <div class="input-group mb-2">
                <span class="input-group-text" id="input-email">Password</span>
                <input v-model="password"
                       type="password" class="form-control" aria-label="Password" aria-describedby="input-pwd">
              </div>
              <div class="my-0">
                <p class="my-0 ps-2 text-start"
                   v-bind:class="!$v.password.containsLowercase ? 'text-warning' : 'text-info fw-bold'">
                  &bull; Lower case <span v-if="$v.password.containsLowercase">&check;</span>
                </p>
                <p class="my-0 ps-2 text-start"
                   v-bind:class="!$v.password.containsUppercase ? 'text-warning' : 'text-info fw-bold'">
                  &bull; Upper case <span v-if="$v.password.containsUppercase">&check;</span>
                </p>
                <p class="my-0 ps-2 text-start"
                   v-bind:class="!$v.password.containsNumber ? 'text-warning' : 'text-info fw-bold'">
                  &bull; Number <span v-if="$v.password.containsNumber">&check;</span>
                </p>
                <p class="my-0 ps-2 text-start"
                   v-bind:class="!$v.password.containsSpecial ? 'text-warning' : 'text-info fw-bold'">
                  &bull; Special character <span class="fst-italic fw-bold">(!@#$%^&*)</span>
                  <span v-if="$v.password.containsSpecial">&check;</span>
                </p>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text" id="input-email">Confirm password</span>
                <input v-model="confirmPassword"
                       type="password" class="form-control" aria-label="Confirm password" aria-describedby="input-conf-pwd">
              </div>
              <div class="my-0" v-if="confirmPassword.length > 0">
                <p class="my-0 ps-2 text-start text-warning" v-if="!$v.confirmPassword.sameAs">&bull; Password do not match</p>
              </div>

              <div class="m-0 text-start">
                <small class="text-muted">Fields marked with '*' are not required.</small>
              </div>

              <div class="mb-2" v-if="errorMsg">
                <p class="text-warning">{{ errorMsg }}</p>
              </div>

              <div class="mb-3">
                <button v-on:click="register"
                        class="btn btn-light px-3 py-2 rounded-pill fw-bold" type="button">
                  <span v-if="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                  Register
                </button>
<!--                <p v-if="submitStatus === 'ERROR'" class="mt-4 text-warning">Please fill the form correctly!</p>-->
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <hr class="m-0 mt-4">
    <Footer></Footer>
  </div>
</template>

<script>
import { required, minLength, maxLength, email, sameAs } from 'vuelidate/lib/validators'
import Header from '@/components/public/Header'
import Footer from '@/components/public/Footer'

export default {
  name: 'RegisterUser',
  components: {
    Header,
    Footer
  },
  data() {
    return {
      firstName: '',
      lastName: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: '',

      submitStatus: '',
      errorMsg: '',
      loading: false
    }
  },
  validations: {
    firstName: {
      required,
      minLength: minLength(1)
    },
    username: {
      required,
      minLength: minLength(3),
      maxLength: maxLength(16)
    },
    email: {
      required,
      email
    },
    password: {
      required,
      minLength: minLength(8),
      // valid: function (value) {
      //   const containsLowercase = /[a-z]/.test(value)
      //   const containsUppercase = /[A-Z]/.test(value)
      //   const containsNumber = /[0-9]/.test(value)
      //   const containsSpecial = /[(!@#$%^&*)]/.test(value)
      //   return containsLowercase && containsUppercase  && containsNumber && containsSpecial
      // },
      containsLowercase: function(value) {
        return /[a-z]/.test(value)
      },
      containsUppercase: function(value) {
        return /[A-Z]/.test(value)
      },
      containsNumber: function(value) {
        return /[0-9]/.test(value)
      },
      containsSpecial: function(value) {
        return /[#?!@$%^&*-]/.test(value)
      }
    },
    confirmPassword: {
      sameAs: sameAs('password')
    }
  },
  methods: {
    register() {
      this.loading = true

      this.$v.$touch()
      if (this.$v.$invalid) {
        this.submitStatus = 'ERROR'
        return
      } else {
        this.submitStatus = 'PENDING'

        const headers = new Headers()
        headers.append('Content-Type', 'application/json')
        const request = new Request('http://localhost:8100/api/v1/users')
        const requestInit = {
          method: 'POST',
          headers: headers,
          body: JSON.stringify({
            first_name: this.firstName,
            last_name: this.lastName,
            username: this.username,
            email: this.email,
            password: this.password
          })
        }
        let hasError = false
        fetch(request, requestInit)
            .then(response => {
              if (response.status === 400) {
                hasError = true
              }
              return response.json()
            })
            .then(data => {
              console.log('json data', data)
              if (hasError) {
                console.log('error', data.message)
                this.errorMsg = data.message
                this.loading = false
                return
              }
              // if success
              // login and redirect
              this.submitStatus = 'OK'
              this.$auth_user.loginWithRedirect({
                redirect_uri: "http://localhost:8080/user"
              });
            })
            .catch(error => {
              console.log('error', error)
            })
      }
    }
  }
}
</script>

<style lang="scss" scoped>

.input-group {
  span {
    background-color: #e0e0e0;
    border: 0;
  }
  input {
    background-color: #2d3f50;
    border: 0;
    color: white;
  }
}

.loading {
  pointer-events: none;
  opacity: .75;
}

</style>