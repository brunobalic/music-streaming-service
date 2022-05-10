<template>
  <div v-if="loadingDone">
    <div class="footer_player">
      <div class="playerLeft">
        <div v-bind:class="[isPlayerActive ? 'playerButtons' : 'playerInactive']">
          <!-- Button - Previous track -->
          <a v-bind:class="[(currentQueueTrackIndex===0 && !loopQueue.isLoopingQueue() && !shuffleQueue.isShuffleEnabled()) ? 'isDisabled' : 'button']"
             v-on:click="playPreviousTrack()" title="Previous Song">
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-player-track-prev" width="30" height="30" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <path d="M21 5v14l-8 -7z"></path>
              <path d="M10 5v14l-8 -7z"></path>
            </svg>
          </a>
          <span style="padding-left: 10px"></span>
          <!-- Button - Play/Pause track -->
          <a class="button" v-on:click="togglePlayPause()" title="Play/Pause Song">
            <transition name="slide-fade" mode="out-in">
              <template v-if="isPlayerActive">
                <i v-if="isPlayerPlaying">
                  <!-- PAUSE button -->
                  <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-player-pause" width="38" height="38" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <rect x="6" y="5" width="4" height="14" rx="1"></rect>
                    <rect x="14" y="5" width="4" height="14" rx="1"></rect>
                  </svg>
                </i>
                <i v-else>
                  <!-- PLAY button -->
                  <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-player-play" width="38" height="38" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M7 4v16l13 -8z"></path>
                  </svg>
                </i>
              </template>
              <template v-else>
                <i>
                  <!-- STOP button -->
                  <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-player-stop" width="38" height="38" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <rect x="5" y="5" width="14" height="14" rx="2"></rect>
                  </svg>
                </i>
              </template>
            </transition>
          </a>
          <span style="padding-right: 10px"></span>
          <!-- Button - Next track -->
          <a :class="[(currentQueueTrackIndex === playerQueue.length - 1 && !loopQueue.isLoopingQueue() && !shuffleQueue.isShuffleEnabled()) ? 'isDisabled' : 'button']"
             v-on:click="playNextTrack()" title="Next Song">
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-player-track-next" width="30" height="30" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <path d="M3 5v14l8 -7z"></path>
              <path d="M14 5v14l8 -7z"></path>
            </svg>
          </a>
        </div>
      </div>
      <div>
        <div class="playerCenter">
          <div style="width: 50px; text-align: right; padding-right: 5px; padding-top: 23px">
            <span class="currentTime">{{ currentElapsedTime | prettyTimeFormat }}</span>
          </div>
          <div style="width: calc(100% - 8rem)">
            <div class="aboveProgressBar">
              <div class="trackInfo">
                <div v-if="playerQueue.length > 0">
                  <span class="artist">{{ playerQueue[currentQueueTrackIndex].artist }}</span>
                  <span> · </span>
                  <span class="track">{{ playerQueue[currentQueueTrackIndex].title }}</span>
                </div>
              </div>
              <div v-bind:class="[isPlayerActive ? 'buttons' : 'btnsDisabled']">
                <div class="opacity-0">placeholder</div>
              </div>
            </div>
            <div class="currentProgressBar">
              <div class="currentProgress" :style="{ width: currentProgressBar + '%' }"></div>
            </div>
          </div>
          <div style="width: 50px; text-align: left; padding-left: 5px; padding-top: 23px">
            <span class="totalTime"> {{ currentTrackDuration | prettyTimeFormat }}</span>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'PlayerAdvancedMinimal',
  data() {
    return {
      loadingDone: false,
      audio: undefined,
      ms: undefined,

      mediaSourceCounter: 0,
      sourceBufferCounter: 0,

      isPreparingAudioResource: false,
      abortController: undefined,
      isPlayerActive: false,
      isPlayerPlaying: false,
      watchTrackTime: undefined,
      currentElapsedTime: 0,
      currentProgressBar: 0,
      playerQueue: [],
      currentQueueTrackIndex: 0,
      loopQueue: {
        /**
         * 0 - loop disabled
         * 1 - loop queue infinitely
         * 2 - loop one track infinitely
         */
        loop: 0,
        trackIndex: undefined,
        // access loop state through these methods
        isSomeLoopEnabled() {
          return this.loop !== 0
        },
        isLoopingDisabled() {
          return this.loop === 0
        },
        isLoopingQueue() {
          return this.loop === 1
        },
        isLoopingSingleTrack() {
          return this.loop === 2
        },
        // change loop state with these methods
        toggleLoop() {
          this.loop = (this.loop + 1) % 3
        },
        disableLoop() {
          this.loop = 0
          this.trackIndex = undefined
        },
        setLoopSingleTrack() {
          this.loop = 2
        },
        setLoopQueue() {
          this.loop = 1
        }
      },
      shuffleQueue: {
        shuffle: false,
        shuffledQueueIndexes: [],
        current: undefined,
        isShuffleEnabled() {
          return this.shuffle
        },
        toggleShuffle() {
          this.shuffle = !this.shuffle
        }
      },
    }
  },
  computed: {
    currentTrackId() {
      return this.playerQueue[this.currentQueueTrackIndex].trackId
    },
    currentTrackDuration() {
      return this.isPlayerActive ? this.playerQueue[this.currentQueueTrackIndex].duration : 0
    }
  },
  mounted() {
    // registering events for controlling player
    this.$root.$on('pl-set-queue-and-play', (queueList) => {
      this.playerQueue = queueList
      this.prepareToPlayNewQueue()
    })
    this.$root.$on('pl-set-queue-and-play-from-index', (queueList, trackIndex) => {
      this.playerQueue = queueList
      this.prepareToPlayNewQueue(trackIndex)
    })
    this.$root.$on('pl-add-to-end-of-queue', (queueList) => {
      this.playerQueue.push(queueList)
      if (!this.isPlayerPlaying) this.prepareToPlayNewQueue()
    })

    // setting up player
    this.audio = new Audio()
    this.audio.addEventListener('canplay', () => {
      if (this.isPlayerPlaying) this.playAudio()
    })
    this.audio.addEventListener('ended', () => {
      this.handleEndedAudio()
    })
    this.ms = new MediaSource()
    this.ms.addEventListener('sourceopen', () => {
      this.handleSourceOpen()
    })
    this.loadingDone = true
  },
  beforeDestroy() {
    clearTimeout(this.watchTrackTime)
  },
  filters: {
    prettyTimeFormat: function (t) {
      return (t - (t %= 60)) / 60 + (9 < t ? ":" : ":0") + t
    }
  },
  methods: {
    activatePlayer() {
      this.isPlayerActive = true
    },
    deactivatePlayer() {
      this.isPlayerActive = false
    },
    currentlyPlaying() {
      return !this.audio.paused
    },
    playAudio() {
      this.getCurrentTimeEverySecond()
      this.audio.play()
      this.isPlayerPlaying = true;
    },
    pauseAudio(interrupted) {
      clearTimeout(this.watchTrackTime)
      this.audio.pause()
      if (!interrupted) this.isPlayerPlaying = false
    },
    togglePlayPause() {
      if (this.currentlyPlaying()) this.pauseAudio()
      else this.playAudio()
    },
    toggleLoop(queueIndex) {
      this.loopQueue.trackIndex = queueIndex
      this.loopQueue.toggleLoop()
      if (this.loopQueue.isLoopingSingleTrack() && this.shuffleQueue.isShuffleEnabled()) {
        this.shuffleQueue.toggleShuffle()
      }
    },
    toggleShuffle() {
      this.shuffleQueue.toggleShuffle()

      // also disable loop single track
      // note: regular loop and shuffle can be both activated at same time
      if (this.shuffleQueue.isShuffleEnabled() && this.loopQueue.isLoopingSingleTrack()) {
        this.loopQueue.disableLoop()
      }
    },
    playNextTrack() {
      if (this.loopQueue.isLoopingSingleTrack()) {
        this.loopQueue.disableLoop()
      }
      if (this.shuffleQueue.isShuffleEnabled()) {
        this.changeTrack(this.getNextRandomTrackIndex())
      } else {
        if (this.currentQueueTrackIndex + 1 < this.playerQueue.length) {
          this.changeTrack(this.currentQueueTrackIndex + 1)
        } else if (this.currentQueueTrackIndex + 1 === this.playerQueue.length && this.loopQueue.isLoopingQueue()) {
          // if loop queue is enabled - start again from beginning
          this.changeTrack(0)
        }
      }
    },
    playPreviousTrack() {
      if (this.loopQueue.isLoopingSingleTrack()) {
        this.loopQueue.disableLoop()
      }
      if (this.shuffleQueue.isShuffleEnabled()) {
        this.changeTrack(this.getNextRandomTrackIndex())
      } else {
        // shuffle is not enabled - change track in order
        if (this.currentQueueTrackIndex > 0) {
          this.changeTrack(this.currentQueueTrackIndex - 1)
        } else if (this.currentQueueTrackIndex === 0 && this.loopQueue.isLoopingQueue()) {
          // if loop queue is enabled - it is possible to go from index 0 to queue end
          this.changeTrack(this.playerQueue.length - 1)
        }
      }
    },
    /////////////////
    getCurrentTimeEverySecond() {
      const vueThis = this
      this.watchTrackTime = setTimeout(
          function () {
            vueThis.currentElapsedTime = Math.round(vueThis.audio.currentTime)
            vueThis.currentProgressBar = vueThis.audio.currentTime / vueThis.currentTrackDuration * 100
            vueThis.getCurrentTimeEverySecond()
          }.bind(this),
          1000
      )
    },
    changeTrack(index, play) {
      const wasPlaying = this.currentlyPlaying()
      if (wasPlaying) this.pauseAudio(true)
      this.currentQueueTrackIndex = index
      this.prepareAudioResource()
      this.currentElapsedTime = 0
      this.currentProgressBar = 0
      if (wasPlaying || play) this.playAudio()
    },
    prepareToPlayNewQueue(startIndex) {
      if (!this.isPlayerActive) this.isPlayerActive = true
      this.changeTrack(startIndex ? startIndex : 0, true)
    },
    handleEndedAudio() {
      if (this.loopQueue.isLoopingQueue()) {
        if (this.shuffleQueue.isShuffleEnabled()) {
          this.changeTrack(this.getNextRandomTrackIndex())
        }
        else {
          if (this.currentQueueTrackIndex + 1 === this.playerQueue.length) {
            this.changeTrack(0)
          }
          else {
            this.changeTrack(this.currentQueueTrackIndex + 1)
          }
        }
      }
      if (this.loopQueue.isLoopingSingleTrack()) {
        this.changeTrack(this.currentQueueTrackIndex)
      }
      if (this.loopQueue.isLoopingDisabled()) {
        // End of queue
        if (this.currentQueueTrackIndex + 1 === this.playerQueue.length) {
          this.pauseAudio()
          this.changeTrack(0)
        }
        else {
          if (this.shuffleQueue.isShuffleEnabled()) this.changeTrack(this.getNextRandomTrackIndex())
          else this.changeTrack(this.currentQueueTrackIndex + 1)
        }
      }
    },
    /////////////////
    prepareAudioResource() {
      if (this.isPreparingAudioResource) {
        // stop preparing previous request and proceed with new request
        this.abortController.abort()
      }
      this.isPreparingAudioResource = true
      this.audio.srcObject ? this.audio.srcObject = this.ms : this.audio.src = URL.createObjectURL(this.ms)
    },
    async handleSourceOpen() {
      if (!this.isPlayerActive) {
        return
      }
      this.sourceBufferCounter += 1

      const vueThis = this
      const mimeCodec = 'audio/mpeg'
      let sb = this.ms.addSourceBuffer(mimeCodec)

      let buffersToAppend = []
      let toReadIndex = 0
      let resourceLoadingComplete = false

      function handleResourceLoadingComplete() {
        resourceLoadingComplete = true
        // in case last buffer is appended before resourceLoadingComplete flag was set
        if (toReadIndex === buffersToAppend.length) vueThis.ms.endOfStream()
      }

      function attemptToAppendBuffer() {
        if (buffersToAppend.length === toReadIndex) {
          return
        }
        if (sb.buffered.length === 1) {
          try {
            if (sb.buffered.end(0) - (sb.buffered.start(0) + vueThis.audio.currentTime) < 60) { // stavi na 180
              console.log('++++ dodajem buffer ', sb.buffered.start(0), sb.buffered.end(0))
              sb.appendBuffer(buffersToAppend[toReadIndex])
            } else {
              console.log('---- necu dodat buffer ', sb.buffered.start(0), sb.buffered.end(0))
            }
          } catch (e) {
            console.log('ERROR: ', e.name, e.message)
          }
        } else {
          sb.appendBuffer(buffersToAppend[toReadIndex])
        }
      }

      function scheduleForAppending(buffer) {
        buffersToAppend.push(buffer)
        if (!sb.updating && (buffersToAppend.length - toReadIndex === 1)) {
          attemptToAppendBuffer()
        }
      }

      sb.addEventListener('updateend', () => {
        buffersToAppend[toReadIndex] = null
        toReadIndex++
        if (toReadIndex < buffersToAppend.length) {
          attemptToAppendBuffer()
        }
        if (resourceLoadingComplete && toReadIndex === buffersToAppend.length) {
          console.log('---- endOfStream() called')
          this.ms.endOfStream()
        }
      })

      vueThis.audio.addEventListener('timeupdate', attemptToAppendBuffer)

      this.abortController = new AbortController()
      this.abortController.signal.addEventListener('abort', () => {
        console.log('---- fetch aborted')
        vueThis.audio.removeEventListener('timeupdate', attemptToAppendBuffer)
      })

      const headers = new Headers()
      headers.append('Authorization', 'Bearer ' + await vueThis.$auth_artist.getTokenSilently())
      const request = new Request(vueThis.playerQueue[vueThis.currentQueueTrackIndex].audioUrl)
      const requestInit = {
        method: 'GET',
        headers: headers,
        signal: vueThis.abortController.signal
      }
      fetch(request, requestInit)
          .then(response => {
            //console.log('@@@headers: ', ...response.headers)
            //console.log('@@@headers: ', response.headers.get('content-type'))
            return response.body
          })
          .then(rs => {
            const reader = rs.getReader()
            return new ReadableStream({
              start(controller) {
                function push() {
                  reader.read().then(({done, value}) => {
                    if (done) {
                      controller.close()
                      handleResourceLoadingComplete()
                      return
                    }
                    controller.enqueue(value);
                    scheduleForAppending(value.buffer)
                    push()
                  })
                }
                push()
              }
            })
          })
          .then(stream => {
            return new Response(stream).arrayBuffer()
          })
          .then(result => {
            console.log('full result is available here', result)
          })
          .catch((e) => {
            console.log('error - fetch:', e)
          })
    },
    // some helpers
    /////////////////
    getRandomInt(maxNonIncluding) {
      return Math.floor(Math.random() * maxNonIncluding)
    },
    getNextRandomTrackIndex() {
      return this.getRandomInt(this.playerQueue.length)
    }
  }
}
</script>

<style lang="scss" scoped>

$playerHeight: 85px;
$playerLeftWidth: 225px;
$playerRightWidth: 300px;
$playerPaddingTop: 23px;
$anim_duration: 0.5s;

.footer_player {
  overflow: hidden;
  background-color: #23232d;
  position: fixed; /* Set the navbar to fixed position */
  /*top: 0; !* Position the navbar at the top of the page *!*/
  bottom: 0;
  width: 100%; /* Full width */
  height: $playerHeight;
  z-index: 2;
  border-top: #505061;
  border-top-width: 1px;
  border-top-style: solid;
}

.animated {
  animation-duration: $anim_duration;
}

.currentTimeContainer {
  width: 100%;
  height: 1rem;
  display: flex;
  justify-content: space-between;
  .currentTime,
  .totalTime {
    font-size: 0.5rem;
    //font-family: monospace;
    color: white;
  }
}

.playerLeft {
  width: $playerLeftWidth;
  height: $playerHeight;
  float: left;
  padding-top: $playerPaddingTop;
  //background-color: blue;
  .playerInactive {
    a {
      svg {
        color: rgb(45, 63, 80);
        fill: rgb(45, 63, 80);
      }
    }
  }
  .playerButtons {
    .button {
      //color: white;
      cursor: pointer;
      svg {
        transition: .2s;
        color: white;
        fill: white;
      }
      &:hover {
        .button {
          cursor: pointer;
        }
        svg {
          transform: scale(1.2);
        }
      }
      &:active {
        svg {
          transform: scale(1.1);
        }
      }
    }
    .isDisabled {
      svg {
        color: rgb(45, 63, 80);
        fill: rgb(45, 63, 80);
      }
    }
  }
}

.playerCenter {
  float: left;
  width: calc(100% - (225px + 300px));
  height: $playerHeight;
  padding-top: $playerPaddingTop;
  padding-left: 20px;
  padding-right: 20px;
  display: flex;
  justify-content: space-between;
  //background-color: red;
  .currentTime,
  .totalTime {
    font-size: 0.7rem;
    //font-family: monospace;
    color: #8f8f9a;
  }
  .currentProgressBar {
    width: 100%;
    background-color: rgb(143, 143, 154);
    margin:0.75rem 0;
    .currentProgress {
      background-color: white;
      width: 0;
      height: 1px;
      transition: 100ms;
    }
  }
  .aboveProgressBar {
    display: flex;
    justify-content: space-between;
    .trackInfo {
      text-align: left;
      color: white;
      .artist {
        color: silver;
      }
      .track {
        color: white;
      }
    }
    .buttons {
      text-align: right;
      .fav svg {
        color: #8f8f9a;
        //height: 80%;
        height: 1.3rem;
        width: auto;
        transition: .2s;
      }
      .fav svg:hover {
        color: #c63a5d;
        fill: #c63a5d;
      }
      .unFav svg {
        color: #c63a5d;
        fill: #c63a5d;
        height: 1.3rem;
        width: auto;
        transition: .2s;
      }
      .unFav svg:hover {
        fill: none;
        color: #8f8f9a;
      }
      .addToPl svg {
        color: #8f8f9a;
        transition: .2s;
      }
      .addToPl svg:hover {
        color: white;
      }
    }
    .btnsDisabled {
      text-align: right;
      pointer-events: none;
      svg {
        color: #8f8f9a;
        height: 1.3rem;
        width: auto;
      }
    }
  }
}

.playerRight {
  width: $playerRightWidth;
  height: $playerHeight;
  float: right;
  padding-top: $playerPaddingTop;
  //background-color: orangered;
  .pl-buttons {
    float: left;
    padding-top: 17px;
    .btn {
      padding: 0 0 0 0;
      margin-right: 5px;
      color: #8f8f9a;
      transition: .2s;
      &:hover {
        transform: scale(1.1);
      }
    }
    .btnActive {
      color: white;
      &:hover {
        transform: scale(1.1);
      }
    }
  }
  .btnsDisabled {
    pointer-events: none;
  }
}

</style>