# dynamic trees with box2d

Inspired by [Badland](https://play.google.com/store/apps/details?id=com.frogmind.badland&hl=de) I created a small
simulation where I test realistic tree behaviour. RevoluteJoins are used to connect the tree parts.
The application is written with [Processing](http://processing.org/) and [Box2D for Processing](https://github.com/shiffman/Box2D-for-Processing)

![GIF](https://raw.githubusercontent.com/leozulfiu/dynamic-trees-box2d/master/screenshots/demo.gif)

## Installation

You need [gradle](http://gradle.org/) to run the application.

## Usage

1. `git clone https://github.com/leozulfiu/dynamic-trees-box2d.git`
2. `cd dynamic-trees-box2d`
3. `gradle run`

For creating a jar file just type `gradle build`. You will find the jar in the build/libs/ folder.
The Start.tmx file is created with [Tiled](http://www.mapeditor.org/). You can change the level to your own desires.

## License

MIT
