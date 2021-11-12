(() => {
  const INTERVAL = 3000
  const MEDIA_ID = location.pathname.split('/').pop()

  const player = document.getElementById('player')
  if (player === null) {
    return
  }

  const getPlaybackTime = () => player.currentTime
  const getProgress = () => getPlaybackTime() / player.duration * 100

  let intervalId
  let playedOnce = false

  const makeRequest = route => {
    fetch('/user-media-consumption/' + MEDIA_ID + '/' + route, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(getProgress())
    })
  }

  player.addEventListener('play', () => {
    if (!playedOnce) {
      makeRequest('play')
      playedOnce = true
    }

    intervalId = setInterval(() => {
      makeRequest('progress')
    }, INTERVAL)
  })

  player.addEventListener('pause', () => {
    clearInterval(intervalId)
  })

  const resumptionButton = document.getElementById('resumption-button')
  if (resumptionButton === null) {
    return
  }

  const resumptionProgress = Number.parseInt(resumptionButton.getAttribute('data-progress'))

  if (Number.isNaN(resumptionProgress) || resumptionProgress < 0) {
    resumptionButton.style.display = 'none'
  }

  resumptionButton.onclick = () => {
    player.currentTime = resumptionProgress / 100 * player.duration
    resumptionButton.style.visibility = 'hidden'
  }
})()
