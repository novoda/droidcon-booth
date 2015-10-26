droidcon-booth
=============
[insert fancy picture of booth here!]

What is it?
==

A device wall which constantly polls github for connected android tests! *"tests......."* 

and **YOU** can add to it!


[Logo](https://github.com/novoda/droidcon-booth/pull/18) | [#enumsmatter](https://github.com/novoda/droidcon-booth/pull/5)
| --- | ---
![logo](https://cloud.githubusercontent.com/assets/1626673/10728185/0a054c52-7be0-11e5-8a75-ab5c1b2bf0dc.gif)|![enumsmatter](https://cloud.githubusercontent.com/assets/466546/10677915/15e2e416-7907-11e5-9cb4-a53df89c1915.gif)

Rules :
==

You can do almost anything *except...*

- Anything **[NSFW](https://en.wikipedia.org/wiki/Not_safe_for_work)**
- Start intents *undecided 
- Start broadcasts
- Take automated pictures
- Evil things to the test runner or devices
- Sound (sorry guys, but we have to stand next to it) 


Stuff to try and avoid
- Touching the production source code. We would prefer you hacked around limitations with reflection
- Network. It's going to be busy, we can't guarantee the devices will be able to handle it.

if you're not sure, do it anyways 


How to take part :
==

Fork this repo https://github.com/novoda/droidcon-booth

Extend https://github.com/novoda/droidcon-booth/blob/master/mobile/src/androidTest/java/com/novoda/canvas/base/NovodaActivityTest.java

Implement `startTestFor`

?????

PROFIT


After you've made your legit test, record a gif of the test and raise a PR against this repo. 
**EZ.**



Recording your test running via android studio's screen recording and then convert to a gif. We use >
`ffmpeg -i dank-test.mp4 -vf scale=360:-1 -r 15 dank-test.gif` but there's also a website for it http://ezgif.com/video-to-gif
