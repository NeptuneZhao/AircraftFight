# AircraftFight

本仓库的[地址](https://github.com/NeptuneZhao/AircraftFight)。
## 介绍

课程[《软件构造》](https://hoa.moe/docs/junior-autumn/comp3059/)（原《面向对象的软件构造导论》）实验内容。

基于 Java Swing 的飞机大战小游戏。

实验会给大家提供一个基本的框架, 要求大家在此基础上完成游戏的功能。

该框架会在第一次实验课前几天以压缩文件的形式同实验指导与 PPT 合并发送到课程群里。

该框架实现了以下功能：

1. 游戏主体, 包含资源、窗口、游戏主循环、控制等；
2. 飞机、子弹、敌机等游戏对象的类及~~为了体现抽象类的功能而硬设置的~~抽象类（如飞行物、战斗机等）；
3. 其他。

本项目完全重写了已提供的代码, 但保留了原有的设计思路, 包括成员命名、类的职责划分等等。

不超过 20 行代码借鉴了学长的[圣遗物](https://github.com/ZSTIH/2022_HITSZ_IOSC-Labs), 感谢开源。

代码约 30% 为 Github Copilot 生成。

## 仓库

使用 IDEA 进行开发, gradle 进行构建。UML 图使用 IDE 自带的插件生成。

原框架使用了 `org.apache.commons.lang3.concurrent.BasicThreadFactory` 来创建线程池并进行管理, 本项目将其进行了替换, 改为使用 Java 自带的 `java.util.concurrent.Executors` 及其他相关类。

## 编译规范

大括号风格: Allman

命名风格: 驼峰(公开成员大写开头, 私有成员小写开头)

缩进: 1 制表符

编码: UTF-8

## 实验内容

### 实验 1

请根据设计的 UML 类图, 重构代码, 在项目中添加精英敌机类和三种道具类, 以及它们的父类。

> 看到没, *以及它们的父类*, 得写个抽象啊！

只需完成本次实现的基本要求, 功能细节将在后续实验中逐步完善。

本实验暂不实现 Boss 机, UML 类图和代码中无需包含 Boss 机。

本次实验提交版本需完成以下功能：

- 每隔一定周期随机产生一架普通敌机或精英敌机；
> 在 `game.Game` 中
- 精英敌机按设定周期直射子弹；
> 在 `game.Game` 中, 由 `game.Game.timeInterval` 控制
- 精英敌机坠毁后随机产生某种向下飞行的道具（或不产生）；
> 由 `flying.Prop.GenerateProp()` 产生
- 英雄机碰撞道具后, 道具自动生效；
> 归为碰撞检测, 见 `game.Game.crashEvent()`
- 加血道具可使英雄机恢复一定血量, 但不超过初始值；
> 见重载的 `flying.prop.PropHealth.TakeEffect()`
- 火力道具和炸弹道具无需具体实现, 生效时只需在控制台打印 `FireSupply active!` `BombSupply active!` 语句即可。
> 早都实现好了, 见 `flying.prop.PropBullet` 和 `flying.prop.PropBomb`

### 实验 2

根据你所设计的 UML 类图, 重构代码, 采用工厂模式创建普通、精英两种敌机, 以及三种道具。

> 这次主要是考验你到底会不会用 idea 的画图功能, 然后把它复制到 PlantUML 里。
>
> 工厂模式见 `flying.warplane.Warplane.GenerateWarplane()`

### 实验 3

单元测试及代码覆盖部分略, 因为太屎了懒得喷。

其他的, 对于代码迭代:

- 超级精英敌机, 随机产生, 向下移动并左右摇晃, 具有散射的三个平行弹道, 掉落道具;
> 见 `flying.warplane.WarplanePlus`
- Boss 机, (我想)弄出个血条, 分数达到一定值后出现, 环形弹道, 平行移动, 掉落 3 道具;
> 见 `flying.warplane.WarplaneBoss`
> 
> 设定出现判定具有分数和时间条件