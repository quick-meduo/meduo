import Mock from 'mockjs2'

export var MeduoMock = {
  on: function (enable, url, type, handle) {
    if (enable) {
      Mock.mock(url, type, handle)
    }
  },
  getBody: (options) => {
    return options.body && JSON.parse(options.body)
  },
  build: (data, message, code = 0, headers = {}) => {
    const responseBody = {}
    responseBody.result = data
    if (message !== undefined && message !== null) {
      responseBody.message = message
    }
    if (code !== undefined && code !== 0) {
      responseBody.code = code
      responseBody._status = code
    }
    if (headers !== null && typeof headers === 'object' && Object.keys(headers).length > 0) {
      responseBody._headers = headers
    }
    responseBody.timestamp = new Date().getTime()
    return responseBody
  },
  getQueryParameters: (options) => {
    const url = options.url
    const search = url.split('?')[1]
    if (!search) {
      return {}
    }
    return JSON.parse('{"' + decodeURIComponent(search)
      .replace(/"/g, '\\"')
      .replace(/&/g, '","')
      .replace(/=/g, '":"') + '"}')
  },
  mock: function (options) {
    return Mock.mock(options)
  }
}
