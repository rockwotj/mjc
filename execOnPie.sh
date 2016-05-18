gradle genARM && scp build/out_ARM.s pi: && ssh pi 'gcc out_ARM.s && ./a.out'
