# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant::Config.run do |config|
  config.vm.box = "precise64"

  config.vm.forward_port 8080, 8080
  config.vm.share_folder "puppet-shelf", "/home/vagrant/puppet-shelf", "."

  config.vm.provision :puppet, :options => ["--debug", "--verbose", "--report", "--summarize", "--reports", "store,summary", "--report_server", "127.0.0.1", "--report_port", "8080", "--reporturl", "http://127.0.0.1:8080/reports"] do |puppet|
    puppet.manifests_path = "manifests"
    puppet.module_path    = "modules"
    puppet.manifest_file  = "site.pp"
  end
end
