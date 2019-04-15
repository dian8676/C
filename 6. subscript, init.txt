import Foundation

 

class Robot{

    var name:String

    var x:Int

    var y:Int

    var distance:Int

    let maxDistance:Int

    let minDistance:Int

    

    init(_ name:String){

        self.name = name

        self.x = 100

        self.y = 100

        self.distance = 5

        self.maxDistance = 200

        self.minDistance = 0

    }

    

    func move(_ direction:String){

        var tmpx = self.x // 범위를 넘을 수 있으므로 임시로 저장

        var tmpy = self.y

        switch direction {

        case "up":

            tmpx = tmpx - self.distance

        case "down":

            tmpx = tmpx + self.distance

        case "left":

            tmpy = tmpy - self.distance

        case "right":

            tmpy = tmpy + self.distance

        case "upleft":

            tmpx = tmpx - self.distance

            tmpy = tmpy - self.distance

        case "upright":

            tmpx = tmpx - self.distance

            tmpy = tmpy + self.distance

        case "downleft":

            tmpx = tmpx + self.distance

            tmpy = tmpy - self.distance

        case "downright":

            tmpx = tmpx + self.distance

            tmpy = tmpy + self.distance

        default:

            print("방향을 잘못입력하셨습니다.")

        }

        if (tmpx >= self.minDistance) && (tmpx <= self.maxDistance)

            && (tmpy >= self.minDistance) && (tmpy <= self.maxDistance){

            self.x = tmpx

            self.y = tmpy

        }

    }

    

    func toString(){

        print(self.name, self.distance, self.x, self.y ,separator:"\\t" ,terminator:"\\t")

    }

    

    subscript (x1:Int, y1:Int, x2:Int, y2:Int) -> Bool{

        //  두 점으로 사각형 범위는 네가지 상황이 생길 수 있다.

        if (x1 <= x2) && (y1 <= y2){ // 1. 왼아래 2. 오른위

            if (self.x >= x1) && (self.x <= x2) && (self.y >= y1) && (self.y <= y2){

                return true

            }else{

                return false

            }

        }else if (x1 <= x2) && (y1 >= y2){ // 1. 왼위 2. 오른아래

            if (self.x >= x1) && (self.x <= x2) && (self.y <= y1) && (self.y >= y2){

                return true

            }else{

                return false

            }

        }else if (x1 >= x2) && (y1 >= y2){ // 1. 오른위 2.왼아래

            if (self.x <= x1) && (self.x >= x2) && (self.y <= y1) && (self.y >= y2){

                return true

            }else{

                return false

            }

        }else{ // 1. 오른아래 2.왼위

            if (self.x <= x1) && (self.x >= x2) && (self.y >= y1) && (self.y <= y2){

                return true

            }else{

                return false

            }

        }

    }

}

 

class CleaningRobot : Robot{

    var cleaningPower:Int

    let maxCleaningPower:Int

    let minCleaningPower:Int

    

    init(_ name:String, _ cleaningPower:Int){

        self.cleaningPower = cleaningPower

        self.maxCleaningPower = 10

        self.minCleaningPower = 0

        super.init(name)

        self.x = 30

        self.y = 30

        self.distance = 3

    }

    func deaningPowerUp(_ power:Int){

        var tmp = self.cleaningPower

        tmp = tmp + power

        if tmp <= self.maxCleaningPower{

            self.cleaningPower = tmp

        }else{

            print("최대 파워(\\(self.maxCleaningPower))를 넘었습니다. 최대값으로 설정합니다.")

            self.cleaningPower = self.maxCleaningPower

        }

    }

    func deaningPowerDown(_ power:Int){

        var tmp = self.cleaningPower

        tmp = tmp - power

        if tmp >= self.minCleaningPower{

            self.cleaningPower = tmp

        }else{

            print("최소 파워(\\(self.minCleaningPower))를 넘었습니다. 최소값으로 설정합니다.")

            self.cleaningPower = self.minCleaningPower

        }

    }

    override func toString() {

        super.toString()

        print("Cleaning Power = ",self.cleaningPower, terminator:"")

    }

}

 

class DogRobot : Robot{

    var barkPower:Int

    let maxBarkPower:Int

    let minBarkPower:Int

    

    init(_ name:String, _ power:Int){

        self.barkPower = power

        self.maxBarkPower = 10

        self.minBarkPower = 0

        super.init(name)

        self.x = 150

        self.y = 150

        self.distance = 10

    }

    

    func barkUp(_ power:Int){

        var tmp = self.barkPower

        tmp = tmp + power

        if tmp <= self.maxBarkPower{

            self.barkPower = tmp

        }else{

            print("최대 파워(\\(self.maxBarkPower))를 넘었습니다. 최대값으로 설정합니다.")

            self.barkPower = self.maxBarkPower

        }

    }

    func barkDown(_ power:Int){

        var tmp = self.barkPower

        tmp = tmp - power

        if tmp >= self.minBarkPower{

            self.barkPower = tmp

        }else{

            print("최소 파워(\\(self.minBarkPower))를 넘었습니다. 최소값으로 설정합니다.")

            self.barkPower = self.minBarkPower

        }

    }

    override func toString() {

        super.toString()

        print("Bark Power = ",self.barkPower, terminator:"")

    }

}

 

enum RobotError : Error {

    case insertNull

}

 

func checkNull(_ input:String) throws{

    guard !(input.isEmpty) else { throw RobotError.insertNull }

}

 

func printRobotList(_ type:Int){

    print("이름\\t거리\\tx\\ty\\tetc")

    print("---------------------------------------")

    if type == 1{

        for i in robotList{

            i.toString()

            print("")

        }

    }else if type == 2{ // 이름 순 정렬

        for i in robotList.sorted(by: {\$0.name < \$1.name}){

            i.toString()

            print("")

        }

    }else if type == 3{// 이름 역순 정렬

        for i in robotList.sorted(by: {\$0.name > \$1.name}){

            i.toString()

            print("")

        }

    }else{

        print("해당하는 번호가 없습니다.")

    }

}

 

func printRobotListWithId(){

    print("ID\\t이름\\t거리\\tx\\ty\\tetc")

    print("---------------------------------------")

    for i in 0..<robotList.count{

        print(i+1, terminator:"\\t")

        robotList[i].toString()

        print("")

    }

}

 

var robotList:[Robot] = []

 

while(true){

    print("=========================")

    print("1. manage robot data")

    print("2. print robot list")

    print("3. move robot")

    print("4. search robot")

    print("=========================")

    print("insert : ", terminator:"")

    let number = readLine()!

    do{

        try checkNull(number)

        if let number = Int(number){

            switch number{

            case 1:

                print("--<MANAGE ROBOT DATA>--")

                print("1. robot data insert")

                print("2. modify robot name")

                print("3. delete robot data")

                print("-----------------------")

                print("insert : ", terminator:"")

                let one = readLine()!

                do{

                    try checkNull(one)

                    if let one = Int(one){

                        switch one{

                        case 1:

                            print("ROBOT DATA INSERT")

                            print("r(robot)")

                            print("c(cleaning robot)")

                            print("d(dog robot)")

                            print("enter type of robot : ",terminator:"")

                            let robotType = readLine()!

                            do{

                                try checkNull(robotType)

                                print("robot name : ", terminator:"")

                                let robotName = readLine()!

                                do{

                                    try checkNull(robotName)

                                }catch{

                                    print("로봇 이름으로 입력된 값이 없습니다.")

                                }

                                switch robotType{

                                case "r":

                                    robotList.append(Robot(robotName))

                                case "c":

                                    print("Cleaning Power : ", terminator:"")

                                    let cPower = readLine()!

                                    do{

                                        try checkNull(cPower)

                                        if let power = Int(cPower){

                                            robotList.append(CleaningRobot(robotName,power))

                                        }else{

                                            print("파워 값 오류")

                                        }

                                    }catch RobotError.insertNull{

                                        print("입력된 파워 값이 없습니다.")

                                    }

                                case "d":

                                    print("Dog Power : ", terminator:"")

                                    let dPower = readLine()!

                                    do{

                                        try checkNull(dPower)

                                        if let power = Int(dPower){

                                            robotList.append(DogRobot(robotName,power))

                                        }else{

                                            print("파워 값 오류")

                                        }

                                    }catch RobotError.insertNull{

                                        print("입력된 파워 값이 없습니다.")

                                    }

                                default:

                                    print("입력된 로봇 종류는 존재하지 않습니다.")

                                }

                            }catch RobotError.insertNull{

                                print("로봇 타입으로 입력된 값이 없습니다.")

                            }

                        case 2:

                            print("MODIFY ROBOT NAME")

                            printRobotListWithId()

                            print("insert id of the robot you want to correct name : ", terminator:"")

                            let modId = readLine()!

                            do{

                                try checkNull(modId)

                                if let modId = Int(modId){

                                    if (modId > 0) && (modId <= robotList.count){

                                        print("insert name to modify : ",terminator:"")

                                        let changeName = readLine()!

                                        do{

                                            try checkNull(changeName)

                                            print(modId,"번째 로봇의 이름을 ",robotList[modId-1].name,"에서", terminator:" ")

                                            robotList[modId-1].name = changeName

                                            print(robotList[modId-1].name,"으로 변경하였습니다.")

                                        }catch RobotError.insertNull{

                                            print("입력된 이름이 없습니다.")

                                        }

                                    }else{

                                        print("해당 로봇이 없습니다.")

                                    }

                                }else{

                                    print("ID 입력 오류")

                                }

                            }catch RobotError.insertNull{

                                print("입력된 ID가 없습니다.")

                            }

                        case 3:

                            print("DELETE ROBOT DATA")

                            printRobotListWithId()

                            print("insert id of the robot you want to delete data : ", terminator:"")

                            let delId = readLine()!

                            do{

                                try checkNull(delId)

                                if let delId = Int(delId){

                                    if (delId > 0) && (delId <= robotList.count){

                                        robotList.remove(at: delId-1)

                                        print("DELETED")

                                    }else{

                                        print("해당 로봇이 없습니다.")

                                    }

                                }else{

                                    print("ID 입력 오류")

                                }

                            }catch RobotError.insertNull{

                                print("입력된 ID가 없습니다.")

                            }

                        default:

                            print("관리 항목에 없는 번호 입니다.")

                        }

                    }else{

                        print("입력 오류")

                    }

                }catch RobotError.insertNull{

                    print("입력된 값이 없습니다.")

                }

                

            case 2:

                print("---<PRINT ROBOT LIST>---")

                print("1. sort by insert time")

                print("2. sort by name")

                print("3. sort by name (reverse)")

                print("-------------------------")

                print("insert : ", terminator:"")

                let two = readLine()!

                do{

                    try checkNull(two)

                    if let twoNum = Int(two){

                        printRobotList(twoNum)

                    }

                }

            case 3:

                print("<MOVE ROBOT>")

                printRobotListWithId()

                print("insert id of the robot you want to move : ",terminator:"")

                let moveId = readLine()!

                do{

                    try checkNull(moveId)

                    if let moveId = Int(moveId){

                        if (moveId > 0) && (moveId <= robotList.count){

                            print("up / down / left / right")

                            print("upleft / upright / downleft / downright")

                            print("insert direction : ", terminator:"")

                            let direction = readLine()!

                            do{

                                try checkNull(direction)

                                print("로봇 ",moveId,"번",terminator:" ")

                                let moveRobot = robotList[moveId-1]

                                if moveRobot is CleaningRobot {

                                    print("Cleaning", terminator:" ")

                                }else if moveRobot is DogRobot{

                                    print("Dog", terminator:" ")

                                }

                                print("로봇을 (\\(moveRobot.x),\\(moveRobot.y))에서", terminator:" ")

                                moveRobot.move(direction)

                                print("(\\(moveRobot.x),\\(moveRobot.y))으로 이동하였습니다.")

                            }catch RobotError.insertNull{

                                print("입력된 방향이 없습니다.")

                            }

                        }else{

                            print("해당 로봇이 없습니다.")

                        }

                    }else{

                        print("ID 입력 오류")

                    }

                }catch RobotError.insertNull{

                    print("입력된 값이 없습니다.")

                }

            case 4:

                print("-------<SEARCH ROBOT>-------")

                print("1. search by robot name")

                print("2. search robot within range")

                print("----------------------------")

                print("insert : ",terminator:"")

                let four = readLine()!

                do{

                    try checkNull(four)

                    if let four = Int(four){

                        switch four{

                        case 1 :

                            print("insert name you want to search : ",terminator:"")

                            let searchName = readLine()!

                            do{

                                try checkNull(searchName)

                                var count = 0

                                print("---------------------------------------")

                                for i in robotList{

                                    if i.name == searchName{

                                        i.toString()

                                        print("")

                                    }else{

                                        count = count + 1

                                    }

                                }

                                if count == robotList.count{

                                    print(searchName,"라는 이름의 로봇은 존재하지 않습니다.")

                                }

                                print("---------------------------------------")

                            }catch RobotError.insertNull{

                                print("입력된 이름이 없습니다.")

                            }

                        case 2 :

                            print("insert (x1 y1 x2 y2) : ",terminator:"")

                            let input = readLine()!

                            do{

                                try checkNull(input)

                                let ranges = input.characters.split(separator: " ").map({Int(String(\$0))!})

                                if ranges.count == 4{

                                    let x1 = ranges[0]

                                    let y1 = ranges[1]

                                    let x2 = ranges[2]

                                    let y2 = ranges[3]

                                    var count = 0

                                    

                                    print("---------------------------------------")

                                    for i in robotList{

                                        if i[x1,y1,x2,y2]{

                                            i.toString()

                                            print("")

                                        }else{

                                            count = count + 1

                                        }

                                    }

                                    if count == robotList.count{

                                        print("해당 범위 안에 존재하는 로봇은 없습니다.")

                                    }

                                    print("---------------------------------------")

                                }else{

                                    print("범위 입력 오류")

                                }

                            }catch RobotError.insertNull{

                                print("입력된 값이 없습니다.")

                            }

                        default:

                            print("해당 숫자가 없습니다.")

                        }

                    }else{

                        print("숫자 입력 오류")

                    }

                }catch RobotError.insertNull{

                    print("입력된 값이 없습니다.")

                }

            default:

                print("해당 번호가 아닙니다.")

            }

        }else{

            print("입력 오류")

        }

        

    }catch RobotError.insertNull{

        print("입력된 값이 없습니다.")

    }

    

}