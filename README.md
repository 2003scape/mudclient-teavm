# mudclient-teavm
runescape classic client transpiled to javascript with
[teavm](https://www.teavm.org/).

## changes

* removed AWT, replaced with [Canvas](http://www.teavm.org/javadoc/0.5.x/jso/apis/org/teavm/jso/canvas/CanvasRenderingContext2D.html) and
[DOM events](http://www.teavm.org/javadoc/0.5.x/jso/apis/org/teavm/jso/dom/events/package-summary.html)

* removed
[java Socket](https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html),
replaced with [Socket shim](https://github.com/2003scape/mudclient-teavm/tree/master/src/main/java/mudclient/Socket.java)
utilizing [teavm WebSocket](https://github.com/konsoletyper/teavm/blob/master/jso/apis/src/main/java/org/teavm/jso/websocket/WebSocket.java)

## build

    $ sudo apt install maven unzip
    $ mvn clean package
    $ cd target
    $ unzip mudclient-teavm-1.0-SNAPSHOT.war
    $ cd teavm/
    $ patch < ../../classes.patch
    $ cd ../ && ln -s ../data204 ./
    $ st -p 1339 # or any other webserver; go to index.html

## license
Copyright 2021  2003Scape Team

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU Affero General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along
with this program. If not, see http://www.gnu.org/licenses/.
