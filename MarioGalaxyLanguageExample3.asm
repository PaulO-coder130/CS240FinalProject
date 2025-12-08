
.data
MESSAGE: .asciiz "Hello!!!!!!!!!!!"

.text
CoinCollect $t7, $t7, 100
talk

PurpleComet $t4

StarbitCollect $t0, $t0, 5
target:
RollingBall

StarbitCollect $t0, $t0, 5
mylabel:

GreenStar $t1

SpeedrunComet mylabel

PlanetJump OTHERLABEL

StarbitCollect $t5, $t5, 100
StarbitShoot $t5, $t5, -50
OTHERLABEL:
HungryLuma $t5, $t7 THELABEL
LumaTransform MESSAGE
#LumaTransform can also be done without printing the message, by using PullStar
THELABEL:
Spin $t5
