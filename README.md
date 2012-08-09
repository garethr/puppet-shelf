# Puppet Shelf

This project is both a Puppet report processor and a web service that
consumes that report and displays in a very visual style the current
state of your puppet agents. This focuses on two things I think are
important; puppet run time and number of failures during a run. It looks
something like this:

## Usage

The report processor is very simple. It formats the report as a small
JSON document and sends it as the payload of an HTTP Post request to a
listening service. It can be found in the modules folder, to install
just drop the shelf module in your Puppet modules directory.

The service is written in Clojure and uses Redis to store run
information. The provided manifests and Vagrantfile should get you up
and running with an Ubuntu 12.04 machine if that's your thing. Runnin
the service is then as simple as:

```bash
lein deps
lein run
```

## License

Copyright (C) 2011 Gareth Rushgrove

Distributed under the Eclipse Public License, the same as Clojure.

