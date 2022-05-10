<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style lang="scss">

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  //color: #2c3e50;
  //color: #5d6982;
  color: silver;
}
#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
html, body {
  height: 100%;
}
body {
  display: flex;
  flex-direction: column;
}
.content {
  flex: 1 0 auto;
}
.footer {
  flex-shrink: 0;
}
button {
  border: none;
  background: transparent;
  padding: 0;
}
.list-group {
  .list-group-item {
    //color: white;
    background-color: transparent;
    border-color: rgb(46, 43, 60);
    text-align: left;
  }
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Thin.ttf");
  font-weight: 100;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-ExtraLight.ttf");
  font-weight: 200;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Light.ttf");
  font-weight: 300;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Regular.ttf");
  font-weight: 400;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Medium.ttf");
  font-weight: 500;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-SemiBold.ttf");
  font-weight: 600;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Bold.ttf");
  font-weight: 700;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-ExtraBold.ttf");
  font-weight: 800;
  font-style: normal;
}
@font-face {
  font-family: myFont2;
  src: url("./assets/fonts/Montserrat/static/Montserrat-Black.ttf");
  font-weight: 900;
  font-style: normal;
}

</style>
