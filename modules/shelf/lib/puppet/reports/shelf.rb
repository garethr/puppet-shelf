require 'puppet'
require 'json'

Puppet::Reports.register_report(:shelf) do

desc <<-DESC
Send a JSON payload to an HTTP endpoint. Formatted for the Puppet Shelf dashboard.
DESC

  def process
    http = Net::HTTP.new('localhost', 8080)
    http.start {
      req = Net::HTTP::Post.new('/reports')
      req.content_type = 'application/json'
      payload = {
        :time => self.metrics['time']['total'],
        :failures => self.metrics['events']['failure'],
        :host => self.host
      }.to_json
      Puppet.debug payload
      req.body = payload
      response = http.request(req)
    }
  end

end
