<template>
  <header class="text-white">
    <div class="container-md p-4">
      <div class="d-flex flex-row align-items-center ps-4 pe-4">
        <a href="/welcome" class="bg-white logo-link">
          <img alt="Logo" src="@/assets/logo2_app.png">
        </a>
        <h2 class="ps-3 mb-0 me-auto">
          BB Music
        </h2>
        <ul class="nav fw-bold fs-5">
          <li><a href="#" class="nav-link px-2 text-white">Features</a></li>
          <li><a href="#" class="nav-link px-2 text-white">Pricing</a></li>
          <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
          <li><a href="#" class="nav-link px-2 text-white">About</a></li>
        </ul>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style lang="scss" scoped>

.logo-link {
  border-radius: 20%;
  img {
    width: 60px;
  }
}
h2 {
  font-family: myFont2, Helvetica, serif;
}

</style>