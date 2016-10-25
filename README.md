droidcon-booth
=============
<img src="https://pbs.twimg.com/media/CSeP7ybW4AA6S5H.jpg:large" width="150px"/>

What is it?
==

A device wall which constantly polls github for connected android tests!

Actually we're not really writing tests here, you caught us! We're using the test runner to call our test suite composed of many **lightning demos** running for 10 seconds each...

and **YOU** can add to it! We want **YOU** to add your creative ideas as another lightning demo and showcase them at our booth.

Here are some of our ideas...

[Logo](https://github.com/novoda/droidcon-booth/pull/18) | [#enumsmatter](https://github.com/novoda/droidcon-booth/pull/5)
| --- | ---
![logo](https://cloud.githubusercontent.com/assets/1626673/10728185/0a054c52-7be0-11e5-8a75-ab5c1b2bf0dc.gif)|![enumsmatter](https://cloud.githubusercontent.com/assets/466546/10677915/15e2e416-7907-11e5-9cb4-a53df89c1915.gif)

Rules :
==

You can do almost anything *except...*

- Anything **[NSFW](https://en.wikipedia.org/wiki/Not_safe_for_work)**
- Start intents that do not target components in this package (Including broadcasts)
- Take automated pictures
- Evil things to the test runner or devices
- Sound
- Touch other peoples tests, cmon we're all buds!
- Write a failing test, no exceptions, force closes, major main thread blocking (a little bit is ok)

Stuff to try and avoid
- Touching the production source code. We would prefer you hacked around limitations with reflection. Adding resources to the main source set is fine though.
- Network. It's going to be busy, we can't guarantee the devices will be able to handle it.

if you're not sure, do it anyways! 

You can always have a look at the [merged pull requests](https://github.com/novoda/droidcon-booth/pulls?q=is%3Apr+is%3Aclosed).

How to Contribute :
==

Fork this repo https://github.com/novoda/droidcon-booth

Extend https://github.com/novoda/droidcon-booth/blob/master/mobile/src/androidTest/java/com/novoda/canvas/base/NovodaActivityTest.java

Implement `startTestFor`

After you've made your *super legit test*, record a gif and raise a PR against this repo. 

**EZ.**

***
You can record your test running via android studio's screen recording and then convert to a gif. We typically use >
`ffmpeg -i dank-test.mp4 -vf scale=360:-1 -r 15 dank-test.gif` 

[@rock3r](https://github.com/rock3r) uses https://gist.github.com/rock3r/a923a79e8d8a850911aa *;) (default transcoding has horrible color artifacts)*

and there's also a website for it http://ezgif.com/video-to-gif
***
While trying your test you might want to run only your own test with something like this

```
./gradlew cAT --debug -Pandroid.testInstrumentationRunnerArguments.class=com.novoda.canvas.HelloWorldTest
```
