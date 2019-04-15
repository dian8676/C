import Foundation

 

class Robot{

    var name:String

    var x:Int

    var y:Int

    var distance:Int

    

    init(_ name:String) {

        self.name = name

        self.x = 100

        self.y = 100

        self.distance = 5

    }

    

    func move(_ direction:Int){

        var tmpx = self.x

        var tmpy = self.y

        switch direction {

        case 1: //상

            tmpx = tmpx - self.distance

        case 2: //하

            tmpx = tmpx + self.distance

        case 3: //left

            tmpy = tmpy - self.distance

        case 4: //right

            tmpy = tmpy + self.distance

        case 5: //upleft

            tmpx = tmpx - self.distance

            tmpy = tmpy - self.distance

        case 6: //upright

            tmpx = tmpx - self.distance

            tmpy = tmpy + self.distance

        case 7: //downleft

            tmpx = tmpx + self.distance

            tmpy = tmpy - self.distance

        case 8: //downright

            tmpx = tmpx + self.distance

            tmpy = tmpy + self.distance

        default:

            print("오류")

        }

        

        if(tmpx >= 0) && (tmpx <= 200){

            self.x = tmpx

        }else if tmpx < 0 {

            self.x = 0

        }else{

            self.x = 200

        }

        if(tmpy >= 0) && (tmpy <= 200){

            self.y = tmpy

        }else if tmpy < 0{

            self.y = 0

        }else{

            self.y = 200

        }

    }

    

    func toStirng(){

        print(name, distance, x, y, separator:"\\t", terminator:"\\t")

    }

    

    subscript (_ name:String) -> Bool {

        if (name == self.name){

            return true

        }

        return false

    }

    subscript (_ x1:Int,_ y1:Int,_ x2:Int,_ y2:Int) -> Bool{

        if(x1 <= x2){

            if (y1 <= y2){

                if(self.x >= x1) && (self.x <= x2) && (self.y >= y1) && (self.y <= y2){

                    return true

                }

            }else{

                if(self.x >= x1) && (self.x <= x2) && (self.y <= y1) && (self.y >= y2){

                    return true

                }

            }

        }else{

            if (y1 <= y2){

                if(self.x <= x1) && (self.x >= x2) && (self.y >= y1) && (self.y <= y2){

                    return true

                }

            }else{

                if(self.x <= x1) && (self.x >= x2) && (self.y <= y1) && (self.y >= y2){

                    return true

                }

            }

        }

        return false

    }

}

 

class CleaningRobot:Robot{

    var cleaningPower:Int

    

    init(_ name: String,_ power:Int) {

        self.cleaningPower = power

        super.init(name)

        self.x = 30

        self.y = 30

        self.distance = 3

    }

    func cleaningPowerUp(_ power:Int){

        let tmp = self.cleaningPower+power

        if tmp > 10{

            print("최대파워는 10입니다. 최대로 설정합니다.")

            self.cleaningPower = 10

        }else{

            self.cleaningPower = tmp

        }

    }

    func cleaningPowerDown(_ power:Int){

        let tmp = self.cleaningPower-power

        if tmp < 0{

            print("최소파워는 0입니다. 최소로 설정합니다.")

            self.cleaningPower = 0

        }else{

            self.cleaningPower = tmp

        }

    }

    override func toStirng() {

        super.toStirng()

        print("Cleaning Power = ",self.cleaningPower,terminator:"")

    }

}

class DogRobot:Robot{

    var barkPower:Int

    

    init(_ name: String,_ power:Int) {

        self.barkPower = power

        super.init(name)

        self.x = 150

        self.y = 150

        self.distance = 10

    }

    func barkUp(_ power:Int){

        let tmp = self.barkPower + power

        if tmp > 10{

            print("최대파워는 10입니다. 최대로 설정합니다.")

            self.barkPower = 10

        }else{

            self.barkPower = tmp

        }

    }

    func barkDown(_ power:Int){

        let tmp = self.barkPower - power

        if tmp < 0{

            print("최소파워는 0입니다. 최소로 설정합니다.")

            self.barkPower = 0

        }else{

            self.barkPower = tmp

        }

    }

    override func toStirng() {

        super.toStirng()

        print("Bark Power = ",self.barkPower,terminator:"")

    }

}

 

enum RobotError : Error{

    case isNull

    case isNumber

}

 

func checkNull(_ input:String) throws{

    guard !(input.isEmpty) else {throw RobotError.isNull}

}

func checkNumber(_ input:String) throws ->Int{

    guard let number = Int(input) else {throw RobotError.isNumber}

    return number

}

func checkDirect(_ input:String) throws{

    //guard//

}

 

var robotList:[Robot] = []

 

robotList.append(Robot("aa"))

robotList.append(CleaningRobot("cc",10))

robotList.append(CleaningRobot("dd",10))

robotList.append(DogRobot("bb",3))

 

func printRobotList(){

    print("id\\t이름\\t거리\\tx\\ty\\tetc")

    print("---------------------------------")

    for i in 0..<robotList.count{

        print(i+1,terminator:"\\t")

        robotList[i].toStirng()

        print("")

    }

    

}

 

func checkMove(_ id:Int,_ robot:Robot,_ direction:Int) -> Bool{

    let tmpRobot:Robot

    if robot is CleaningRobot {

        tmpRobot = CleaningRobot("",0)

    }else if robot is DogRobot {

        tmpRobot = DogRobot("",0)

    }else{

        tmpRobot = Robot("")

    }

    tmpRobot.x = robot.x

    tmpRobot.y = robot.y

    tmpRobot.move(direction)

    for i in 0..<robotList.count{

        if(robotList[i].x == tmpRobot.x) && (robotList[i].y == tmpRobot.y) && (id-1 != i){

            print("로봇 ",id,"번을 (\\(robot.x),\\(robot.y))에서 (\\(tmpRobot.x),\\(tmpRobot.y))으로 이동하려고 했으나, \\(i+1)번",terminator:" ")

            if robotList[i] is CleaningRobot{

                print("Cleaning",terminator:" ")

            }else if robotList[i] is DogRobot{

                print("Dog",terminator:" ")

            }

            print("로봇이 있어 이동하지 못했습니다.")

            return false

        }

    }

    return true

}

 

enum Direct:Int{

    case up = 1

    case down = 2

    case left = 3

    case right = 4

    case upleft = 5

    case upright = 6

    case downleft = 7

    case downright = 8

    case other

}

 

while(true){

    print("===============")

    print("1. 로봇데이터관리")

    print("2. 로봇리스트보기")

    print("3. 개별로봇이동")

    print("4. 로봇검색")

    print("===============")

    print("입력 : ",terminator:"")

    let numberInput = readLine()!

    do{

        try checkNull(numberInput)

        let number = try checkNumber(numberInput)

        

        switch number{

        case 1:

            print("로봇데이터관리")

            print("1. 로봇 데이터 추가")

            print("2. 로봇 데이터 이름 수정")

            print("3. 로봇 데이터 삭제")

            print("입력 : ",terminator:"")

            let oneInput = readLine()!

            try checkNull(oneInput)

            let one = try checkNumber(oneInput)

            switch one{

            case 1 :

                print("로봇 이름 입력 : ",terminator:"")

                let name = readLine()!

                try checkNull(name)

                print("r:Robot, c:CleaningRobot, d:DogRobot")

                print("로봇 종류 입력 : ",terminator:"")

                let type = readLine()!

                try checkNull(type)

                switch type{

                case "r":

                    robotList.append(Robot(name))

                case "c":

                    print("power : ",terminator:"")

                    let powerInput = readLine()!

                    try checkNull(powerInput)

                    let power = try checkNumber(powerInput)

                    robotList.append(CleaningRobot(name,power))

                case "d":

                    print("power : ",terminator:"")

                    let powerInput = readLine()!

                    try checkNull(powerInput)

                    let power = try checkNumber(powerInput)

                    robotList.append(DogRobot(name,power))

                default:

                    print("해당하는 종류가 없습니다.")

                }

                

            case 2:

                print("로봇 이름 수정")

                printRobotList()

                print("수정할 로봇 번호 입력 : ",terminator:"")

                let modIdInput = readLine()!

                try checkNull(modIdInput)

                let modId = try checkNumber(modIdInput)

                if (modId >= 1) && (modId <= robotList.count){

                    print("수정할 이름 입력 : ",terminator:"")

                    let modName = readLine()!

                    do{

                        try checkNull(modName)

                        robotList[modId-1].name = modName

                        print(modName,"으로 변경하였습니다.")

                    }catch RobotError.isNull{

                        print("[null]입력된 값이 없습니다.")

                    }

                }else{

                    print("해당 로봇은 존재하지 않습니다.")

                }

                

            case 3:

                print("로봇 데이터 삭제")

                printRobotList()

                print("삭제할 로봇 번호 입력 : ",terminator:"")

                let delIdInput = readLine()!

                try checkNull(delIdInput)

                let delId = try checkNumber(delIdInput)

                if (delId >= 1) && (delId <= robotList.count){

                    robotList.remove(at: delId - 1)

                    print("삭제하였습니다.")

                }else{

                    print("해당 로봇은 존재하지 않습니다.")

                }

                

            default:

                print("해당 숫자가 아닙니다.")

            }

        case 2:

            print("로봇 리스트 보기")

            print("1.로봇 이름 순 정렬")

            print("2.로봇 이름 역순 정렬")

            print("3.로봇 위치 x->y순 정렬")

            print("입력 : ",terminator:"")

            let two = readLine()!

            try checkNull(two)

            let twoNum = try checkNumber(two)

            switch twoNum{

            case 1:

                print("이름순정렬")

                print("이름\\t거리\\tx\\ty\\tetc")

                print("---------------------------------")

                for i in robotList.sorted(by: {\$0.name < \$1.name}){

                    i.toStirng()

                    print("")

                }

            case 2:

                print("이름역순정렬")

                print("이름\\t거리\\tx\\ty\\tetc")

                print("---------------------------------")

                for i in robotList.sorted(by: {\$0.name > \$1.name}){

                    i.toStirng()

                    print("")

                }

            case 3:

                print("로봇 위치")

                print("이름\\t거리\\tx\\ty\\tetc")

                print("---------------------------------")

                for i in robotList.sorted(by: {(s1:Robot,s2:Robot)->Bool in

                    if s1.x==s2.x { return s1.y<s2.y }

                    return s1.x<s2.x}){

                        i.toStirng()

                        print("")

                }

            default:

                print("해당 숫자가 아닙니다.")

            }

        case 3:

            printRobotList()

            print("이동할 로봇 번호 입력 : ",terminator:"")

            let move = readLine()!

            try checkNull(move)

            let moveId = try checkNumber(move)

            if(moveId > 0) && (moveId <= robotList.count){

                print("이동선택 (up,down,left,right,upleft,upright,downleft,downright) : ",terminator:"")

                let direction = readLine()!

                try checkNull(direction)

                let moveRobot = robotList[moveId - 1]

                var dr:Direct = .other

                

                switch direction {

                case "up" :

                    dr = .up

                case "down":

                    dr = .down

                case "left":

                    dr = .left

                case "right":

                    dr = .right

                case "upleft" :

                    dr = .upleft

                case "upright":

                    dr = .upright

                case "downleft":

                    dr = .downleft

                case "downright":

                    dr = .downright

                default:

                    dr = .other

                    print("해당 방향은 존재하지 않습니다.")

                }

                

                if (dr != .other) && (checkMove(moveId, moveRobot, dr.rawValue)){

                    print("로봇 ",moveId,"번을 (\\(moveRobot.x),\\(moveRobot.y))에서 ",terminator:"")

                    moveRobot.move(dr.rawValue)

                    print("(\\(moveRobot.x),\\(moveRobot.y))으로 이동하였습니다.")

                }

            }else{

                print("해당 로봇은 존재하지 않습니다.")

            }

        case 4:

            print("로봇 검색")

            print("1. 로봇 명으로 검색")

            print("2. (x1,y1,x2,y2)입력해서 포함된 로봇 검색")

            print("입력 : ",terminator:"")

            let four = readLine()!

            try checkNull(four)

            let fourNum = try checkNumber(four)

            switch fourNum{

            case 1:

                print("검색할 이름 입력 : ",terminator:"")

                let searchName = readLine()!

                try checkNull(searchName)

                

                let searchArr = robotList.filter({\$0[searchName]})

                print("이름\\t거리\\tx\\ty\\tetc")

                print("---------------------------")

                for i in searchArr{

                    i.toStirng()

                    print("")

                }

            case 2:

                print("x1,y1,x2,y2 입력 (띄어쓰기로 구분) : ",terminator:"")

                let inputNum = readLine()!

                try checkNull(inputNum)

                let numbers = try inputNum.split(separator: " ").map({try checkNumber(String(\$0))})

                if numbers.count == 4{

                    let x1 = numbers[0]

                    let y1 = numbers[1]

                    let x2 = numbers[2]

                    let y2 = numbers[3]

                    

                    var count = 0

                    print("이름\\t거리\\tx\\ty\\tetc")

                    print("---------------------------------")

                    for i in robotList{

                        if i[x1,y1,x2,y2]{

                            i.toStirng()

                            print("")

                        }else{

                            count = count+1

                        }

                    }

                    if count == robotList.count{

                        print("범위안에 로봇이 없습니다.")

                    }

                }

            default:

                print("해당 숫자가 아닙니다.")

            }

        default:

            print("해당 숫자가 아닙니다.")

        }

        

    }catch RobotError.isNull{

        print("[null]입력된 값이 없습니다.")

    }catch RobotError.isNumber{

        print("[number]입력된 값이 숫자가 아닙니다.")

    }

    

}

 