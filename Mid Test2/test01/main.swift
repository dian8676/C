import Foundation

class LibraryBook{
    var id:Int
    var title:String
    var locx:Int
    var locy:Int
    var rentName:String
    
    init(_ id:Int,_ title:String,_ locx:Int,_ locy:Int){
        self.id = id
        self.title = title
        self.locx = locx
        self.locy = locy
        self.rentName = " - "
    }
    
    func rent(_ name:String)->Bool{
        if rentName == " - "{
            self.rentName = name
            return true
        }
        return false
    }
    
    func returnBook(){
        self.rentName = " - "
    }
    
    func toString(){
        print(self.id,self.title,self.locx,self.locy,self.rentName,separator:"\t",terminator:"\t")
    }
    
    subscript (_ title:String) -> Bool{
        if self.title == title {
            return true
        }
        return false
    }
    subscript (_ x1:Int, _ y1:Int, _ x2:Int, _ y2:Int)->Bool{
        if(x1<x2)&&(y1<y2){
            if(self.locx >= x1) && (self.locy >= y1) && (self.locx <= x2) && (self.locy <= y2){
                return true
            }
        }else{
            if(self.locx <= x1) && (self.locy <= y1) && (self.locx >= x2) && (self.locy >= y2){
                return true
            }
        }
        return false
        
    }
}

class Book:LibraryBook{
    var author:String
    var publisher:String
    var page:Int
    
    init(_ id:Int,_ title:String,_ locx:Int,_ locy:Int,_ author:String,_ publisher:String,_ page:Int){
        self.author = author
        self.publisher = publisher
        self.page = page
        super.init(id,title, locx, locy)
    }
    override func toString(){
        print(self.id,"Book",self.title,self.locx,self.locy,self.rentName,separator:"\t",terminator:"\t")
        print("<",self.author,",",self.publisher,",",self.page,">")
    }
}

class DVD:LibraryBook{
    var director:String
    var filmStudio:String
    var numOfDVD:Int
    
    init(_ id:Int,_ title:String,_ locx:Int,_ locy:Int,_ director:String,_ filmStudio:String,_ numOfDVD:Int){
        self.director = director
        self.filmStudio = filmStudio
        self.numOfDVD = numOfDVD
        super.init(id,title, locx, locy)
    }
    override func toString(){
        print(self.id,"DVD ",self.title,self.locx,self.locy,self.rentName,separator:"\t",terminator:"\t")
        print("<",self.director,",",self.filmStudio,",",self.numOfDVD,">")
    }
}

var listLibrary:[LibraryBook] = []

enum libraryError:Error {
    case isNull
    case isNumber
}

func checkNull(_ input:String) throws{
    guard !(input.isEmpty) else {throw libraryError.isNull}
}
func checkNumber(_ input:String) throws -> Int{
    guard let number = Int(input) else {throw libraryError.isNumber}
    return number
}


func listPrint(){
    print("id\t종류\t제목\tlocx\tlocy\t대여자\t기타정보")
    print("-------------------------------------------------------")
    for i in listLibrary.sorted(by: {$0.id < $1.id}){
        i.toString()
    }
}

func searchId(_ id:Int)->Int{
    for i in 0..<listLibrary.count{
        if listLibrary[i].id == id{
            return i
        }
    }
    return listLibrary.count
}

listLibrary.append(Book(5,"zz",30, 50,"mm","ss",100))
listLibrary.append(Book(6,"gg",35, 50,"cc","xd",105))
listLibrary.append(DVD(7,"zz",30, 59,"ef","xx",10))

while(true){
    print("1. 도서관 책 관리")
    print("2. LibraryBook 리스트 보기")
    print("3. LibraryBook 대여 및 이동")
    print("4. LibraryBook 검색")
    
    print("입력 : ",terminator:"")
    let number = readLine()!
    do{
        try checkNull(number)
        let number = try checkNumber(number)
        
        switch(number){
        case 1:
            print("<도서관 책 관리>")
            print("1. LibraryBook 데이터 추가")
            print("2. 책 제목순으로 정렬해서 보기")
            print("3. LibraryBook 데이터 삭제")
            print("입력 : ",terminator:"")
            let oneInput = readLine()!
            try checkNull(oneInput)
            let one = try checkNumber(oneInput)
            
            switch(one){
            case 1:
                print("1. LibraryBook 데이터 추가")
                print("종류 선택(1:book, 2:dvd) : ",terminator:"")
                let kindInput = readLine()!
                try checkNull(kindInput)
                let kind = try checkNumber(kindInput)
                
                if kind == 1 { //book
                    print("입력 (id title x y author publisher page)\n : ",terminator:"")
                    let inputData = readLine()!
                    try checkNull(inputData)
                    let data = inputData.split(separator: " ").map({String($0)})
                    if data.count == 7{
                        let dataId = try checkNumber(data[0])
                        let dataX = try checkNumber(data[2])
                        let dataY = try checkNumber(data[3])
                        let dataPage = try checkNumber(data[6])
                        
                        listLibrary.append(Book(dataId,data[1],dataX,dataY,data[4],data[5],dataPage))
                        
                    }else{
                        print("입력 갯수가 다릅니다.")
                    }
                }else if kind == 2{
                    print("입력 (id title x y director filmStudio numOfDVD)\n : ",terminator:"")
                    let inputData = readLine()!
                    try checkNull(inputData)
                    let data = inputData.split(separator: " ").map({String($0)})
                    if data.count == 7{
                        let dataId = try checkNumber(data[0])
                        let dataX = try checkNumber(data[2])
                        let dataY = try checkNumber(data[3])
                        let dataPage = try checkNumber(data[6])
                        
                        listLibrary.append(DVD(dataId,data[1],dataX,dataY,data[4],data[5],dataPage))
                        
                    }else{
                        print("입력 갯수가 다릅니다.")
                    }
                }else{
                    print("해당 종류는 없습니다.")
                }
            case 2:
                print("2. 책 제목순으로 정렬해서 보기")
                listPrint()
            case 3:
                print("3. LibraryBook 데이터 삭제")
                listPrint()
                print("입력 : ",terminator:"")
                let deleteInput = readLine()!
                try checkNull(deleteInput)
                let deleteNum = try checkNumber(deleteInput)
                
                if (deleteNum >= 0) && (deleteNum < listLibrary.count){
                    let id = searchId(deleteNum)
                    if id != listLibrary.count{
                        listLibrary.remove(at: id)
                        print("삭제되었습니다.")
                    }else{
                        print("일치하는 번호가 없습니다.")
                    }
                }
            default:
                print("해당 번호는 없습니다.")
            }
        case 2:
            print("<LibraryBook 리스트 보기>")
            print("1. LibraryBook 이름 역순으로 정렬해서 보기")
            print("2. locx -> loxy 순으로 정렬해서 보기")
            print("입력 : ",terminator:"")
            let twoInput = readLine()!
            try checkNull(twoInput)
            let two = try checkNumber(twoInput)
            
            if two == 1{
                print("이름 역순")
                print("id\t종류\t제목\tlocx\tlocy\t대여자\t기타정보")
                print("-------------------------------------------------------")
                for i in listLibrary.sorted(by: {$0.title > $1.title}){
                    i.toString()
                }
            }else if two==2{
                print("locx->loxy 순")
                print("id\t종류\t제목\tlocx\tlocy\t대여자\t기타정보")
                print("-------------------------------------------------------")
                for i in listLibrary.sorted(by: {
                    (s1:LibraryBook, s2:LibraryBook)->Bool in
                    if s1.locx == s2.locx {return s1.locy < s2.locy}
                    return s1.locx < s2.locx
                }){
                    i.toString()
                }
                
            }else{print("해당 숫자는 없습니다.")}
        case 3:
            print("<LibraryBook 대여 및 이동>")
            print("1. LibraryBook 빌리고 반납하기")
            print("2. LibraryBook 서가 이동하기")
            print("입력 : ",terminator:"")
            let threeInput = readLine()!
            try checkNull(threeInput)
            let three = try checkNumber(threeInput)
            
            if three == 1{
                listPrint()
                print("빌릴 책 id : ",terminator:"")
                let rentIdInput = readLine()!
                try checkNull(rentIdInput)
                let rentId = try checkNumber(rentIdInput)
                let id = searchId(rentId)
                if id != listLibrary.count{
                    print("빌릴 사람 이름 : ",terminator:"")
                    let rentName = readLine()!
                    try checkNull(rentName)
                    
                    if listLibrary[id].rent(rentName) {
                        print(rentId,"번 책을 대여하였습니다.")
                    }else{
                        print(rentId,"번 책은 이미 ",listLibrary[id].rentName,"에게 대여되었습니다.")
                    }
                }
                
                
            }else if three == 2{
                // 구현안됨
            }else{
                print("해당 숫자는 없습니다.")
            }
            
        case 4:
            print("<LibraryBook 검색>")
            print("1. 책 제목으로 검색")
            print("2. (x1,y1,x2,y2)입력해서 포함된 책 검색하기")
            print("입력 : ",terminator:"")
            
            let fourInput = readLine()!
            try checkNull(fourInput)
            let four = try checkNumber(fourInput)
            
            if four == 1{
                print("책 제목 입력 : ",terminator:"")
                let title = readLine()!
                try checkNull(title)
                print("id\t종류\t제목\tlocx\tlocy\t대여자\t기타정보")
                print("-------------------------------------------------------")
                for i in listLibrary.filter({$0[title]}){
                    i.toString()
                }
            }else if four == 2{
                print("x1 : ",terminator:"")
                let x1Input = readLine()!
                try checkNull(x1Input)
                let x1 = try checkNumber(x1Input)
                print("y1 : ",terminator:"")
                let y1Input = readLine()!
                try checkNull(y1Input)
                let y1 = try checkNumber(y1Input)
                print("x2 : ",terminator:"")
                let x2Input = readLine()!
                try checkNull(x2Input)
                let x2 = try checkNumber(x2Input)
                print("y2 : ",terminator:"")
                let y2Input = readLine()!
                try checkNull(y2Input)
                let y2 = try checkNumber(y2Input)
                
                print("id\t종류\t제목\tlocx\tlocy\t대여자\t기타정보")
                print("-------------------------------------------------------")
                for i in listLibrary.filter({$0[x1,y1,x2,y2]}){
                    i.toString()
                }
                
            }
        default:
            print("해당 번호는 없습니다.")
        }
        
        
        
        
    }catch libraryError.isNull{
        print("[Error]입력된 값이 없습니다.")
    }catch libraryError.isNumber{
        print("[Error]숫자가 아닙니다.")
    }
    
    
}



