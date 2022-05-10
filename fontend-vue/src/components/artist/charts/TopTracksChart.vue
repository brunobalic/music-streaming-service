<template>
  <div class="chart rounded-3 p-3">
    <h6>Total number of plays for the last 30 days.</h6>
    <canvas id="ch-top-tr"></canvas>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js'

Chart.register(...registerables)

export default {
  name: 'TopTracksChart',
  props: {
    stats: {
      type: Array,
      required: true
    },
    tracksToDisplay: {
      type: Number
    }
  },
  data() {
    return {
      myChart: undefined
    }
  },
  mounted() {
    const ctx = document.getElementById('ch-top-tr')

    let data = []
    let daylist = this.getDaysArray(new Date(new Date().setDate(new Date().getDate()-29)) ,new Date());
    daylist.map((v)=>v.toISOString().slice(0,10)).join("")
    let newDayList = daylist.map(v => v.toLocaleDateString('en-GB', {day: 'numeric', month: 'long', year: 'numeric'}))

    let dayLabels = [...Array(30).keys()].reverse()
    dayLabels.forEach((el, index)  => dayLabels[index] = dayLabels[index] + 1)

    this.myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: dayLabels,
        datasets: [{
          label: 'Play count',
          data: data,
          borderColor: [
            'rgb(198,228,245)'
          ],
          borderWidth: 1.5
        }]
      },
      options: {
        scales: {
          x: {
            ticks: {
              font: {
                size: 14,
              },
              color: 'rgba(200,200,200)'
            }
          },
          y: {
            grid: {
              color: 'rgba(200,200,200)',
              borderColor: 'rgba(200,200,200)'
            },
            ticks: {
              font: {
                size: 14,
              },
              color: 'rgba(200,200,200)'
            }
          }
        },
        plugins: {
          legend: {
            labels: {
              font: {
                size: 15
              }
            }
          },
          tooltip: {
            callbacks: {
              footer: (tooltipItem) => {
                return newDayList[tooltipItem[0].dataIndex]
              }
            }
          }
        }
      }
    })
  },
  methods: {
    getDaysArray(start, end) {
      let arr = []
      for(let dt = new Date(start); dt <= new Date(end); dt.setDate(dt.getDate() + 1)){
        arr.push(new Date(dt));
      }
      return arr;
    }
  }
}
</script>

<style lang="scss" scoped>

.chart {
  background-color: #2d3f50;
}

</style>