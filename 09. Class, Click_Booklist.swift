import UIKit

 

class ViewController: UIViewController,UITextFieldDelegate {

    

    @IBOutlet weak var nameTextField: UITextField!

    @IBOutlet weak var authorTextField: UITextField!

    @IBOutlet weak var defaultButton: UIButton!

    @IBOutlet weak var bookListLabel: UILabel!

    @IBOutlet weak var bookCountLabel: UILabel!

    @IBOutlet weak var authorListLabel: UILabel!

    

    class Book{

        var name:String

        var author:String

        

        init(_ name:String,_ author:String){

            self.name = name

            self.author = author

        }

        

        func toString() -> String{

            return self.name+"\\t"+self.author+"\\n"

        }

    }

    

    var bookList:[Book] = []

    

    override func viewDidLoad() {

        super.viewDidLoad()

        

        nameTextField.delegate = self

        authorTextField.delegate = self

        // Do any additional setup after loading the view, typically from a nib.

    }

 

    //메모리 경고 받으면 할 일

    override func didReceiveMemoryWarning() {

        super.didReceiveMemoryWarning()

        // Dispose of any resources that can be recreated.

    }

    

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {

        nameTextField.resignFirstResponder()

        authorTextField.resignFirstResponder()

        returntrue

    }

 

    @IBAction func clickDefaultBook(_ sender: Any) {

        bookList.append(Book(nameTextField.text!,authorTextField.text!))

        bookCountLabel.text = "책 \\(bookList.count)권"

        bookListLabel.text = ""

        authorListLabel.text = ""

        for i in bookList{

            bookListLabel.text?.append(i.name+"\\n")

            authorListLabel.text?.append(i.author+"\\n")

        }

        nameTextField.text = ""

        authorTextField.text = ""

    }

    

}