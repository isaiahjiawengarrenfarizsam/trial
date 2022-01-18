/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toSubmit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

 class slidingImage extends JFrame  implements ActionListener
{
ImageIcon s[];
JLabel l;
JButton b1,b2;
int i,l1;
JPanel p;
public slidingImage(int i)
{
    JPanel p=new JPanel(new FlowLayout());
    
    switch (i) {
        case (1) :
    setLayout(new BorderLayout( ));
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setVisible(true);
    b1=new JButton("↩");
    b2=new JButton("↪");
    p.add(b1);
    p.add(b2);
    add(p,BorderLayout.SOUTH);
    b1.addActionListener(this);
    b2.addActionListener(this);
    s = new ImageIcon[3]; 
    s[0] = new ImageIcon("C:\\Images\\1 guest 1.jpg");
    s[1] = new ImageIcon("C:\\Images\\1 guest 2.jpg");
    s[2] = new ImageIcon("C:\\Images\\1 guest 3.jpg");
    l = new JLabel("",JLabel.CENTER); 
    add(l,BorderLayout.CENTER);
    l.setIcon(s[0]);
    break;
    
        case (2) :
            setLayout(new BorderLayout( ));
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setVisible(true);
    b1=new JButton("↩");
    b2=new JButton("↪");
    p.add(b1);
    p.add(b2);
    add(p,BorderLayout.SOUTH);
    b1.addActionListener(this);
    b2.addActionListener(this);
    s = new ImageIcon[3]; 
    s[0] = new ImageIcon("C:\\Images\\2 guests 1.jpg");
    s[1] = new ImageIcon("C:\\Images\\2 guests 2.jpg");
    s[2] = new ImageIcon("C:\\Images\\2 guests 3.jpg");
    l = new JLabel("",JLabel.CENTER); 
    add(l,BorderLayout.CENTER);
    l.setIcon(s[0]);
     break;
     
    case (3) :
        setLayout(new BorderLayout( ));
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setVisible(true);
    b1=new JButton("↩");
    b2=new JButton("↪");
    p.add(b1);
    p.add(b2);
    add(p,BorderLayout.SOUTH);
    b1.addActionListener(this);
    b2.addActionListener(this);
    s = new ImageIcon[3]; 
    s[0] = new ImageIcon("C:\\Images\\3 guests 1.jpg");
    s[1] = new ImageIcon("C:\\Images\\3 guests 2.jpg");
    s[2] = new ImageIcon("C:\\Images\\3 guests 3.jpg");
    l = new JLabel("",JLabel.CENTER); 
    add(l,BorderLayout.CENTER);
    l.setIcon(s[0]);
     break;
     
    case (4) :
        setLayout(new BorderLayout( ));
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setVisible(true);
    b1=new JButton("↩");
    b2=new JButton("↪");
    p.add(b1);
    p.add(b2);
    add(p,BorderLayout.SOUTH);
    b1.addActionListener(this);
    b2.addActionListener(this);
    s = new ImageIcon[3]; 
    s[0] = new ImageIcon("C:\\Images\\4 guests 1.jpg");
    s[1] = new ImageIcon("C:\\Images\\4 guests 2.jpg");
    s[2] = new ImageIcon("C:\\Images\\4 guests 3.jpg");
    l = new JLabel("",JLabel.CENTER); 
    add(l,BorderLayout.CENTER);
    l.setIcon(s[0]);
     break;
     
    case (5) :
        setLayout(new BorderLayout( ));
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setVisible(true);
    b1=new JButton("↩");
    b2=new JButton("↪");
    p.add(b1);
    p.add(b2);
    add(p,BorderLayout.SOUTH);
    b1.addActionListener(this);
    b2.addActionListener(this);
    s = new ImageIcon[3]; 
    s[0] = new ImageIcon("C:\\Images\\5 guests 1.jpg");
    s[1] = new ImageIcon("C:\\Images\\5 guests 2.jpg");
    s[2] = new ImageIcon("C:\\Images\\5 guests 3.jpg");
    l = new JLabel("",JLabel.CENTER); 
    add(l,BorderLayout.CENTER);
    l.setIcon(s[0]);
     break;
    }   
}
public  void actionPerformed(ActionEvent e)
{
    if(e.getSource()==b1)
    {
        if(i==0)
        {
            JOptionPane.showMessageDialog(null,"First image of the list.");
        }
        else
            {
            i=i-1;
            l.setIcon(s[i]);
        }
    }
    if(e.getSource()==b2)
    {
        if(i==s.length-1)
        {
            JOptionPane.showMessageDialog(null,"You have reached the last image.");
        }
        else
            {
            i=i+1;
            l.setIcon(s[i]);
            }
        }
     } 
 }

