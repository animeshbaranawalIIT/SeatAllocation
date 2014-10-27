NAME                   ROLL NUMBER                   Contribution   
Animesh Baranawal      130050013                     100%
Rawal Khirodkar        130050014                     100%
Lokit Kumar Paras      130050047                     100%


Honour Code: We pledge on our honour that we have not given or received any kind of unauthorized assistance on this task or any 
other previous task.

Question 1
The command "dmesg" prints the "kernel ring buffer"
When a pen drive is inserted, the dmesg prints the information about the pen-drive like device number, idVendor, idProduct, serial number, Product and Manufacturer of the USB DEVICE
( This can be displayed by " dmesg | tail " )

The lsusb command lists all the USB devices.

Yes, we can access the contents of the pen drive via command line if we know its residing partition.
And then " cd /media "

THE RAM memory of the system can be found using " dmesg | grep -i "RAMDISK" and then converting the hexadecimal numbers to decimal numbers.
RAM range in hexadecimal -  0x359a0000-0x36cc7fff
RAM range in decimal -      0.89 Gb - 0.91 Gb (approx)
