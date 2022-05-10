<template>
  <div class="container-md loading">
    <ol class="list-group list-group-flush">
      <li v-for="i in repeat" v-bind:key="i"
          class="list-group-item pt-3 pb-3">
        <div class="row">
          <div class="col-md-3 item-box1"></div>
          <div class="col-md d-flex flex-column text-start">
            <p class="item-line1"></p>
            <p class="item-line2"></p>
            <p class="item-line3 mb-auto"></p>
            <div class="d-flex flex-row">
              <div class="item-btn1 me-3"></div>
              <div class="item-btn1"></div>
            </div>
          </div>
        </div>
      </li>
    </ol>
  </div>
</template>

<script>
export default {
  name: 'LoadingScreenAlbumsList',
  props: {
    repeat: {
      type: Number,
      required: false,
      default: 1
    }
  }
}
</script>

<style lang="scss" scoped>

.loading {
  .item-box1,
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
  .item-box1 {
    width: 155px;
    height: 155px;
    border-radius: 5%;
  }
  .item-btn1 {
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
  }
  .item-line1 {
    width: 200px;
    height: 1.75rem;
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