<template>
  <div class="container-md mt-4 ps-5 pe-5 loading">
    <div class="row g-0">
      <div class="col-md-4 m-4"
           style="max-width: 200px">
        <div class="item-circle text-start"></div>
      </div>
      <div class="col-md m-4 d-flex flex-column text-start">
        <h3 class="item-line1 mb-3"></h3>
        <p class="item-line3"></p>
        <p class="item-line2"></p>
        <p class="mb-auto item-line3"></p>
        <div class="d-flex flex-row">
          <div class="item-btn1"></div>
          <div class="item-line3 align-self-center ms-3"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoadingScreenArtistProfile'
}
</script>

<style lang="scss" scoped>

.loading {
  .item-circle,
  .item-btn1,
  .item-line1,
  .item-line2,
  .item-line3 {
    //background: #eee;
    opacity: 50%;
    background: #2d3f50 linear-gradient(
            100deg,
            rgba(255, 255, 255, 0) 40%,
            rgb(80, 100, 120) 50%,
            rgba(255, 255, 255, 0) 60%
    );
    border-radius: 5px;
    background-size: 200% 100%;
    background-position-x: 180%;
    animation: 3s loading ease-in-out infinite;
  }
  .item-circle {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    //border-bottom-left-radius: 0;
    //border-bottom-right-radius: 0;
  }
  .item-btn1 {
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
  }
  .item-line1 {
    width: 300px;
    height: 2rem;
  }
  .item-line2 {
    width: 100px;
    height: 1rem;
  }
  .item-line3 {
    width: 150px;
    height: 1rem
  }
}

@keyframes loading {
  to {
    background-position-x: -25%;
  }
}

</style>