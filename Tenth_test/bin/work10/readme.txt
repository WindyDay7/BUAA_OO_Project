第9次作业readme
输入格式：
1.对于普通的乘客指令输入样例为：[CR,(1,2),(2,3)]，若是输入的目的地不符合规范，则不予以处理，输入当中不允许有空格，前导0和+号。
2.若是需要获取所有出租车的信息，在控制台输入information；
3.仅仅支持在所有命令的开始进行load file操作：控制台输入形式如：Load E:\\text.txt，对于文件内部的输入，需要按如下输入：
#map
#end_map
#light
E:\\trafficLight.txt
#end_light
#flow
(0,0),(0,1),100
#end_flow
#taxi
30 2 3 (6,7)
60 1 5 
#end_taxi
#request
[CR,(10,10),(20,20)]
[CR,(10,20),(20,50)]
#end_request
不支持空格和前导0以及+号，在创建新地图时会重新new一个地图，所以会有两张地图，但只能对新的地图进行操作。对于taxi的操作，若是不输入想要去的位置则会随机选择一个位置，但是在输入信用数字之后必须还要一个空格，不然会crash。
4：开关道路的操作:输入的样例为：o|c (1,2),(2,3) 不支持空格前导0和+号。其中o代表开一条被关闭的道路，c代表关一条已经存在的道路。
若是要修改初始地图，可在main的52行修改。若是需要修改，输出的文件路径在54行。可自行修改。
5：关于红绿灯文件的说明：在刚开始的时候地图上是没有红绿灯的，只有在Load 文件之后，并且在#light与#end_light之间有一个正确的存在的文件才会在地图上显示出红绿灯。红绿灯文件的内容请保证一定要和地图的文件符合（例如路口才能有红绿灯），然后每一行只能有80个0或者1，共计80行。请保证红绿灯文件的正确性。
输出说明：
所有的输出均在同一个文件当中，具体的格式在schedule和taxi中。报错也在文件当中，即控制台没有输出。
另外：对于大量的数据可能会出现程序的崩溃现象，若是对正在接客的出租车进行额外的调度（改位置和状态）他仍然会跑这一单。
